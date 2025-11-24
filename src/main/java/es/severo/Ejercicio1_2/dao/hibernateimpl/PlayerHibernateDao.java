package es.severo.Ejercicio1_2.dao.hibernateimpl;

import es.severo.Ejercicio1_2.dao.PlayerDao;
import es.severo.Ejercicio1_2.domain.Player;

public class PlayerHibernateDao extends GenericHibernateDao<Player, Long> implements PlayerDao {
    public PlayerHibernateDao(){
        super(Player.class);
    }


}
