package es.severo.dao;

import es.severo.domain.Space;
import org.hibernate.Session;

public interface GenericDao<T, ID> {

    ID create(Session session, T entity);
    T update(Session session, T enetity);
    void delete(Session session, T entity);
    T findById(Session session, ID id);
    boolean deleteById(Session session, ID id);

}
