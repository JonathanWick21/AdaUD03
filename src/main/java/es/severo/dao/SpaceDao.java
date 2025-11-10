package es.severo.dao;

import es.severo.domain.Space;
import es.severo.domain.User;
import es.severo.dto.MostProfitSpaces;
import org.hibernate.Session;

import java.util.List;

public interface SpaceDao extends GenericDao<Space, Long>{
    Space updateSpaceWithUser(Space s, User user);

    List<MostProfitSpaces> findTop3MostProfitSpaces(Session session);

    List<Space> spacesNotBooked(Session session);

}
