package es.severo;

import es.severo.config.HibernateUtil;
import es.severo.domain.Space;
import es.severo.service.SpaceService;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
    try {


        SpaceService spaceService = new SpaceService();
        System.out.println(spaceService.getSpaceByCode("MR-001"));

        System.out.println(spaceService.getMostProfitSpaces());

        System.out.println(spaceService.nonBookedSpaces());
    } catch (PersistenceException e){
        System.err.println(e.getMessage());
        e.printStackTrace();
    }
//        Space s = new Space();
//        s.setCode("MR-001");
//        s.setActive(true);
//        s.setCapacity(10);
//        s.setHourlyPrice(new BigDecimal("20.5"));
//        s.setName("Meeting Room");
//        s.setType(Space.SpaceType.MEETING_ROOM);
//
//        try {
//            spaceService.createSpace(s, 1L);
//        } catch (PersistenceException e){
//            System.err.println(e.getMessage());
//        }
    }
}
