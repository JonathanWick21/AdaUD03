package es.severo.service;


import es.severo.config.HibernateUtil;
import es.severo.dao.SpaceDao;
import es.severo.dao.VenueDao;
import es.severo.dao.hibernateimpl.SpaceHibernateDao;
import es.severo.dao.hibernateimpl.VenueHibernateDao;
import es.severo.domain.Space;
import es.severo.domain.Tag;
import es.severo.domain.Venue;
import es.severo.dto.MostProfitSpaces;
import es.severo.dto.SpacenameVenueCity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
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

    // los 3 espacios con más ingresos generados, de reservas confirmadas
    //codigo, nombre y total generado, descendente del total

    public List<MostProfitSpaces> getMostProfitSpaces(){
        Transaction tx = null;

        try {
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            List<MostProfitSpaces> list = spaceDao.findTop3MostProfitSpaces(session);


            tx.commit();

            return list;
        } catch (PersistenceException e){
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }

    //Queremos obtener los espacios que nunca has sido reservados --> SQL nativa

    public List<Space> nonBookedSpaces(){
        Transaction tx = null;
        try {
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            List<Space> lista = spaceDao.spacesNotBooked(session);

            tx.commit();

            return lista;
        } catch (PersistenceException e){
            if (tx != null)
                tx.rollback();
            throw e;
        }
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
    /*
            Queremos una lista de espacios que están activos, tienen una capacidad mínima
            y un precio máximo por hora, ordenados por precios asc y capacidad desc
             */

    public List<Space> espaciosActivos(int maxPrice, int minCap){
        Transaction tx = null;
        try {
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Space> cq = cb.createQuery(Space.class);

            Root<Space> root = cq.from(Space.class);

            cq.select(root)
                            .where(cb.and(
                               cb.isTrue(root.get("active")),
                                    cb.gt(root.get("capacity"), minCap),
                                    cb.lt(root.get("hourlyPrice"), maxPrice)
                            ))
                    .orderBy(cb.asc(root.get("hourlyPrice")), cb.desc(root.get("capacity")));

            tx.commit();

            return session.createQuery(cq).getResultList();

        } catch (PersistenceException e){
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }



    /*
    CRITERIA
     */

    public void testCriteria(){
        Transaction tx = null;
        try {
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Space> cq = cb.createQuery(Space.class);
            Root<Space> root = cq.from(Space.class);
            Join<Space, Tag> joinTag = root.join("tags");


//            Devuelve todos los espacios cuyo nombre contenga ELX
//            y que tienen al menos un Tag llamado wifi
            cq.select(root)
                    .where(
                            cb.and(
                                    cb.like(root.get("name"), "%ELX%"),
                                    cb.equal(cb.lower(joinTag.get("name")), "wifi")
                            )
                    );



//            //todos los espacios activos
//            cq.select(root)
//                            .where(
//                                    cb.and(
//                                    cb.gt(root.get("capacity"), 10),
//                                    cb.isTrue(root.get("active")),
//                                    cb.equal(root.get("name"), ""))
//                            )
//                    .groupBy()
//                    .having()
//                    .orderBy(cb.asc(root.get("name")),
//                            cb.desc(root.get("")));
//            List<Space> spaces = session.createQuery(cq).getResultList();


            //todos los espacios de la sede de alicante

//            cq.select(cb.construct(
//                    SpacenameVenueCity.class,
//                            root.get("name"),
//                            joinVenue.get("city")))
//
//                            .where(cb.equal(joinVenue.get("city"), "Alicante"));
//            List<SpacenameVenueCity> spaces = session.createQuery(cq).getResultList();




            tx.commit();
        } catch (PersistenceException e){
            if (tx != null){
                tx.rollback();
            }
            throw e;
        }
    }

}
