package es.severo.service;


import es.severo.config.HibernateUtil;
import es.severo.dao.SpaceDao;
import es.severo.dao.hibernateimpl.SpaceHibernateDao;
import org.hibernate.SessionFactory;

/*
HQL/JPQL --> orientado o objetos
    NamedQuery --> entidad
    -querys: space.name

SQL
Nativas --> service, dao createNativeQuery

Criteria

3 formas de devolver información de varias tablas:
- Object[]
- Tuple
- DTO * record

Métodos para recoger los resultados:
-getResultList()
-getSingleResult()
-getSingleResultOrNull()



 */

public class TagService {
    private final SessionFactory sessionFactory;
    private final SpaceDao spaceDao;

    public TagService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.spaceDao = new SpaceHibernateDao();
    }
}
