package es.severo.service;


import es.severo.config.HibernateUtil;
import es.severo.dao.SpaceDao;
import es.severo.dao.VenueDao;
import es.severo.dao.hibernateimpl.SpaceHibernateDao;
import es.severo.dao.hibernateimpl.VenueHibernateDao;
import es.severo.domain.Space;
import es.severo.domain.Venue;
import es.severo.dto.SpacenameVenueCity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SpaceService {

    private SessionFactory sf;
    private final SpaceDao spaceDao;
    private final VenueDao venueDao;

    public SpaceService() {
        this.sf = HibernateUtil.getSessionFactory();
        this.spaceDao = new SpaceHibernateDao();
        this.venueDao = new VenueHibernateDao();
    }

    public Long createSpace(Space space, Long venueId){
        Transaction tx = null;
        try {
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            //Comprobar si existe
            Venue sede = venueDao.findById(session, venueId);
            if (sede == null){
                throw new EntityNotFoundException("No existe la sede: " +  venueId);
            }
            space.setVenue(sede);
            Long id = spaceDao.create(session, space);

            tx.commit();
            return id;
        }catch (PersistenceException e){
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }
    public List<SpacenameVenueCity> getSpaceByCode(String code){
        if (!code.isEmpty() && !code.isBlank()){
            Transaction tx = null;
            try{
                Session session = sf.getCurrentSession();
                tx = session.beginTransaction();

                //HQL / JPQL
                List<Venue> venues = session
                        .createNamedQuery("Venue.findByName", Venue.class)
                        .setParameter("name", "plaza madrid")
                        .getResultList();

                //NATIVAS
                session.createNativeQuery("select * from venues v where v.name = :nom", Venue.class);



                TypedQuery<SpacenameVenueCity> query = session.createQuery(
                        "select s.name, v.city from Space s join s.venue v", SpacenameVenueCity.class);
                List<SpacenameVenueCity> rows = query.getResultList();

                tx.commit();
                return rows;
            } catch (PersistenceException e){
                if (tx != null)
                    tx.rollback();
                throw e;
            }
        }else
            throw new PersistenceException("Codigo inválido");
    }
}
