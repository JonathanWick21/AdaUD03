package es.severo.Ejercicio1_2.dao.hibernateimpl;

import es.severo.Ejercicio1_2.dao.GenericDao;
import org.hibernate.Session;

public class GenericHibernateDao<T, ID> implements GenericDao<T, ID> {

    private Class<T> entityClass;

    public GenericHibernateDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public ID create(Session s, T entity) {
        s.persist(entity);
        s.flush();
        return (ID) s.getIdentifier(entity);
    }

    @Override
    public T findById(Session s, ID id) {
        return s.find(entityClass, id);
    }

    @Override
    public T update(Session s, T entity) {
        return s.merge(entity);
    }

    @Override
    public void delete(Session s, T entity) {
        s.remove(entity);
    }

    @Override
    public boolean deleteById(Session s, ID id) {
        T entity = s.find(entityClass, id);
        if (entity != null){
            s.remove(entity);
            return true;
        }
        return false;
    }
}
