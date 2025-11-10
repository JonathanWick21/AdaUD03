package es.severo.dao.hibernateimpl;

import es.severo.dao.GenericDao;
import es.severo.dao.SpaceDao;
import es.severo.domain.Space;
import es.severo.domain.User;
import es.severo.dto.MostProfitSpaces;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class SpaceHibernateDao extends GenericHibernateDao<Space, Long> implements SpaceDao{

    public SpaceHibernateDao(){
        super(Space.class);

    }

    @Override
    public Space updateSpaceWithUser(Space s, User user) {
        return null;
    }

    @Override
    public List<MostProfitSpaces> findTop3MostProfitSpaces(Session session) {
        String query = "select new es.severo.dto.MostProfitSpaces(b.space.code, b.space.name, sum(b.totalPrice)) " +
                "from Booking b " +
                "where b.status = es.severo.domain.Booking.BookingStatus.CONFIRMED " +
                "group by b.space.id, b.space.name " +
                "order by sum(b.totalPrice) desc";

        return session.createQuery(query, MostProfitSpaces.class)
                .setMaxResults(3)
                .getResultList();
    }

    @Override
    public List<Space> spacesNotBooked(Session session) {
        String query = """
                SELECT s.* FROM spaces s LEFT JOIN bookings b ON s.id = b.space_id WHERE b.space_id is null
                """;

        return session.createNativeQuery(query, Space.class)
                .getResultList();
    }
}
