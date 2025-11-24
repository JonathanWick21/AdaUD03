package es.severo.Ejercicio1_2.service;

import es.severo.Ejercicio1_2.domain.Player;
import es.severo.Ejercicio1_2.domain.RfidCard;
import es.severo.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PlayerService {
    private SessionFactory sf;
    private final Player player;
    private final RfidCard rfidCard;

    public PlayerService() {
        this.sf = HibernateUtil.getSessionFactory();
        this.player = new Player();
        this.rfidCard = new RfidCard();
    }

    public boolean emitirRfidCard(){
        Transaction tx = null;

        try {
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();


        }
    }
}
