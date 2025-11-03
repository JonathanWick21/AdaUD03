package es.severo.dao.hibernateimpl;

import es.severo.dao.GenericDao;
import es.severo.dao.SpaceDao;
import es.severo.domain.Space;
import es.severo.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SpaceHibernateDao extends GenericHibernateDao<Space, Long> implements SpaceDao{

    public SpaceHibernateDao(){
        super(Space.class);

    }

    @Override
    public Space updateSpaceWithUser(Space s, User user) {
        return null;
    }
}
