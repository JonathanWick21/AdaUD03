package es.severo.service;


import es.severo.config.HibernateUtil;
import es.severo.dao.SpaceDao;
import es.severo.dao.VenueDao;
import es.severo.dao.hibernateimpl.SpaceHibernateDao;
import es.severo.dao.hibernateimpl.VenueHibernateDao;
import es.severo.domain.Space;
import es.severo.domain.Venue;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

            //Comprobar si existeç
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
}
