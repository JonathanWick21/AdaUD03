package es.severo.dao.hibernateimpl;

import es.severo.dao.VenueDao;
import es.severo.domain.Venue;
import org.hibernate.Session;

public class VenueHibernateDao extends GenericHibernateDao<Venue, Long> implements VenueDao {

    public VenueHibernateDao() {
        super(Venue.class);
    }
}
