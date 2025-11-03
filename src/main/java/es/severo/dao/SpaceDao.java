package es.severo.dao;

import es.severo.domain.Space;
import es.severo.domain.User;
import org.hibernate.Session;

public interface SpaceDao extends GenericDao<Space, Long>{

    Space updateSpaceWithUser(Space s, User user);

}
