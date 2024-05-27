package demo.db;

import utils.ItemAlreadyExistsException;

import org.eclipse.persistence.config.QueryHints;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Date;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

public class Runner {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo_DB_jar_1.0-SNAPSHOTPU");

//    public static List<String> getAllArtistNames() throws Exception {
    public static List<String> getAllArtistNames() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<String> query = em.createQuery("SELECT a.artistName FROM Artist a", String.class);
            List<String> artistNames = query.getResultList();
            return artistNames;
        } catch (Exception e) { // general exception, as it can send DatabaseException or runtime, and all treated the same way.
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public static List<Show> getShowsByArtist(String artistName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Show> query = em.createQuery(
                    "SELECT s FROM Show s WHERE s.artName.artistName = :artistName", Show.class);
            query.setParameter("artistName", artistName);
            List<Show> allShows = query.getResultList();
            return allShows;
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public static Tickets getAllTickets(String showID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Tickets> query = em.createQuery(
                    "SELECT t FROM Tickets t WHERE t.showId.showId = :show_id", Tickets.class);
            query.setParameter("show_id", showID);
            query.setMaxResults(1);
            Tickets ticket = query.getSingleResult();
            return ticket;
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            em.close();
        }
    }

    public static boolean[] getAvailableMSeatsByTicket(Tickets ticket) throws IllegalAccessException {
        List<Boolean> availableSeats = new ArrayList<>();
        try {
            for (Field field : Tickets.class.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(ticket);
                if ((fieldValue instanceof Boolean) && fieldName.startsWith("seat")){
                    availableSeats.add((boolean)fieldValue);
                }
            }
        } catch (IllegalAccessException e) {
            throw e;
        }
        boolean[] seatsArray = new boolean[availableSeats.size()];
        for (int i = 0; i < availableSeats.size(); i++) {
            seatsArray[i] = availableSeats.get(i);
        }
        return seatsArray;
    }

    public static Integer getUMSeatsByTicket(Tickets ticket){
        return ticket.getUnmarkedLeft();
    }

    public static boolean makeMOrder(int row, int seat, String showId, String userMail, String phoneNumber){
        try {
            if (bookedSeat(row, seat, showId)) {
                return addMOrder(row, seat, showId, userMail, phoneNumber);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static Boolean isSeatAvailable(String columnName, String showid) {
        String request = "SELECT t." + columnName + " FROM Tickets t WHERE t.showId.showId = :show_id AND t." + columnName + " = true";
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery(request);
            query.setParameter("show_id", showid);
            List<Boolean> resultList = query.getResultList();
            return !resultList.isEmpty();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public static synchronized Boolean bookedSeat(int row, int seat, String showId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            String columnName = "seat" + row + seat;
            Boolean isAv = isSeatAvailable(columnName, showId);
            if (!isAv){
                return false;
            }
            String request = "UPDATE Tickets t SET t." + columnName + " = false WHERE t.showId.showId = :show_id";
            tx.begin();

            // Execute the query
            Query query = em.createQuery(request);
            query.setParameter("show_id", showId);

            // Find the updated ticket object
            Tickets ticket = em.createQuery("SELECT t FROM Tickets t WHERE t.showId.showId = :show_id", Tickets.class)
                    .setParameter("show_id", showId)
                    .getSingleResult();
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error in bookedSeat", e);

        } finally {
            em.close();
        }
    }

    public static synchronized Boolean addMOrder(int row, int seat, String showId, String userMail, String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Orders order = new Orders();
        order.setOrderId();
        order.setWebUserId(getUserByMailPhone(userMail, phoneNumber));
        order.setShowId(getShowByID(showId));
        order.setWhichTicket(row + "_" + seat);
        order.setTicketsId(getTicketByShowID(showId));

        try {
            tx.begin();
            em.persist(order);

            if (tx.getRollbackOnly()) {
                tx.rollback();
                throw new Exception("Transaction failed.");
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public static boolean makeUMOrder(int seatAmount, String showId, String userMail, String phoneNumber){ //need to add try&catch
        try {
            if (bookedUnmarkedSeat(seatAmount, showId)) {
                return addUMOrder("um_" + seatAmount, showId, userMail, phoneNumber);
            }
        } catch (Exception e) {
        return false;
        }
        return false;
    }

    public static synchronized Boolean bookedUnmarkedSeat(int seatAmount, String showId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Tickets ticket = getTicketByShowID(showId);
            int newUnmarkedLeft = ticket.getUnmarkedLeft() - seatAmount;
            if (newUnmarkedLeft >= 0) {
                ticket.setUnmarkedLeft(newUnmarkedLeft);
                String tID = ticket.getTicketsId();
                em.merge(ticket); // merge the updated object back into the persistence context
                em.createQuery("UPDATE Tickets t SET t.unmarkedLeft = :newUnmarkedLeft WHERE t.ticketsId = :ticketId")
                        .setParameter("newUnmarkedLeft", newUnmarkedLeft)
                        .setParameter("ticketId", tID)
                        .executeUpdate();
            } else {
                return false;
            }
            if (tx.getRollbackOnly()) {
                throw new Exception("Transaction failed.");
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public static synchronized Boolean addUMOrder(String whichTicket, String showId, String userMail, String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Orders order = new Orders();
        order.setOrderId();
        order.setWebUserId(getUserByMailPhone(userMail, phoneNumber));
        order.setShowId(getShowByID(showId));
        order.setWhichTicket(whichTicket);
        order.setTicketsId(getTicketByShowID(showId));

        try {
            tx.begin();
            em.persist(order);

            if (tx.getRollbackOnly()) {
                tx.rollback();
                throw new Exception("Transaction failed.");
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback(); //todo: need to change to tx
            return false;
        } finally {
            em.close();
        }
    }

    public static WebUser getUserByMailPhone(String userMail, String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<WebUser> query = em.createQuery(
                    "SELECT u FROM WebUser u WHERE u.emailAddress = :Email_address AND u.phoneNumber = :Phone_Number", WebUser.class);
            query.setParameter("Email_address", userMail);
            query.setParameter("Phone_Number", phoneNumber);
            return query.getSingleResult();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public static Show getShowByID(String showid){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Show> query = em.createQuery(
                    "SELECT s FROM Show s WHERE s.showId = :show_id", Show.class);
            query.setParameter("show_id", showid);
            return query.getSingleResult();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public static Tickets getTicketByShowID (String showId){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Tickets> query = em.createQuery("SELECT t FROM Tickets t WHERE t.showId.showId = :show_id", Tickets.class);
        query.setParameter("show_id", showId);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public static List<String[]> getOrdersByArtistAndShow(String artistName, String showLocation) {
        List<String[]> results = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        try {
            String queryString = "SELECT w.First_Name, w.Last_Name, w.Email_address, w.Phone_Number, o.Which_Ticket " +
                    "FROM Web_user w " +
                    "JOIN Orders o ON w.Web_User_ID = o.Web_User_ID " +
                    "JOIN Tickets t ON o.Tickets_ID = t.Tickets_ID " +
                    "JOIN Show s ON o.Show_ID = s.Show_ID " +
                    "JOIN Artist a ON s.Art_Name = a.Artist_Name " +
                    "WHERE a.Artist_Name = ?1 " +
                    "AND s.Show_Location = ?2 " +
                    "AND (t.Unmarked_left > 0 " +
                    "AND (t.Seat1_1 = TRUE OR t.Seat1_2 = TRUE OR t.Seat1_3 = TRUE OR t.Seat1_4 = TRUE OR t.Seat1_5 = TRUE " +
                    "OR t.Seat2_1 = TRUE OR t.Seat2_2 = TRUE OR t.Seat2_3 = TRUE OR t.Seat2_4 = TRUE OR t.Seat2_5 = TRUE " +
                    "OR t.Seat3_1 = TRUE OR t.Seat3_2 = TRUE OR t.Seat3_3 = TRUE OR t.Seat3_4 = TRUE OR t.Seat3_5 = TRUE " +
                    "OR t.Seat4_1 = TRUE OR t.Seat4_2 = TRUE OR t.Seat4_3 = TRUE OR t.Seat4_4 = TRUE OR t.Seat4_5 = TRUE " +
                    "OR t.Seat5_1 = TRUE OR t.Seat5_2 = TRUE OR t.Seat5_3 = TRUE OR t.Seat5_4 = TRUE OR t.Seat5_5 = TRUE))";

            Query query = em.createNativeQuery(queryString);
            query.setParameter(1, artistName);
            query.setParameter(2, showLocation);

            List<Object[]> resultList = query.getResultList();
            for (Object[] result : resultList) {
                String[] row = new String[5];
                row[0] = (String) result[0];
                row[1] = (String) result[1];
                row[2] = (String) result[2];
                row[3] = (String) result[3];
                row[4] = (String) result[4];
//                row[5] = (String) result[5];
//                row[6] = (String) result[6];
                results.add(row);
            }
            return results;
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public static synchronized Artist addArtist(String artistName) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Artist artist = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            artist = new Artist();
            artist.setArtistName(artistName);
            artist.setArtistId();
            em.persist(artist);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return artist;
    }

    public static synchronized void addShow(String artName, String showLocation, Time showTime, Date showDate, Integer unmarkedAmount) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            Artist art = getArtist(artName);
            if (art == null){
                art = addArtist(artName);
            }

            //check if show already exists
            TypedQuery<Show> query = em.createQuery("SELECT s FROM Show s WHERE s.artName = :art AND s.showLocation = :location AND s.showTime = :time AND s.showDate = :date", Show.class);
            query.setParameter("art", art);
            query.setParameter("location", showLocation);
            query.setParameter("time", showTime);
            query.setParameter("date", showDate);
            List<Show> existingShows = query.getResultList();
            if (!existingShows.isEmpty()) {
                throw new ItemAlreadyExistsException();
            }

            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Show show = new Show();
            show.setShowId();
            show.setArtName(art);
            show.setShowLocation(showLocation);
            show.setShowTime(showTime);
            show.setShowDate(showDate);

            em.persist(show);

            if (tx.getRollbackOnly()) {
                throw new Exception("Transaction failed.");
            }
            tx.commit();
            //if show added, add tickets:
            addTickets(show.getShowId(), unmarkedAmount);
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public static Artist getArtist(String artistName) {
        EntityManager em = null;
        Artist artist = null;
        try {
            em = emf.createEntityManager();
            Query query = em.createQuery("SELECT a FROM Artist a WHERE a.artistName = :name");
            query.setParameter("name", artistName);

            artist = (Artist) query.getSingleResult();
            return artist;
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
            return artist;
        }
    }

    public static synchronized void addTickets(String showId, int numOfUnmarkedSeats) {
        EntityManager em = emf.createEntityManager();
        EntityManager emSec = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            Show show = em.createQuery("SELECT s FROM Show s WHERE s.showId = :show_id", Show.class)
                    .setParameter("show_id", showId)
                    .getSingleResult();
            em.close();
            emSec = emf.createEntityManager();
            tx = emSec.getTransaction();
            tx.begin();

            Tickets ticket = new Tickets();
            ticket.setTicketsId();
            ticket.setShowId(show);
            ticket.setUnmarkedSeats(numOfUnmarkedSeats);
            ticket.setUnmarkedLeft(numOfUnmarkedSeats);
            ticket.setSeat11(true);  // for the reviewer: no way found to do that dynamically, as the java version needed for jsf doesn't support several options that would make it more readable.
            ticket.setSeat12(true);
            ticket.setSeat13(true);
            ticket.setSeat14(true);
            ticket.setSeat15(true);
            ticket.setSeat21(true);
            ticket.setSeat22(true);
            ticket.setSeat23(true);
            ticket.setSeat24(true);
            ticket.setSeat25(true);
            ticket.setSeat31(true);
            ticket.setSeat32(true);
            ticket.setSeat33(true);
            ticket.setSeat34(true);
            ticket.setSeat35(true);
            ticket.setSeat41(true);
            ticket.setSeat42(true);
            ticket.setSeat43(true);
            ticket.setSeat44(true);
            ticket.setSeat45(true);
            ticket.setSeat51(true);
            ticket.setSeat52(true);
            ticket.setSeat53(true);
            ticket.setSeat54(true);
            ticket.setSeat55(true);

            emSec.persist(ticket);
            if (tx.getRollbackOnly()) {
                throw new Exception("Transaction failed.");
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            emSec.close();
        }
    }

    public static synchronized int validateUser(String email, String phoneNum) {
        int equalsMailAndPhone = 0;
        int equalsMailOrPhone = 1;
        int equalsNotMailNotPhone = -1;
        int otherError = 99;

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<WebUser> query = em.createQuery("SELECT u FROM WebUser u WHERE u.emailAddress = :Email_address", WebUser.class);
            query.setParameter("Email_address", email);
            WebUser user = query.getSingleResult();

            if (user.getPhoneNumber().equals(phoneNum)) {
                return equalsMailAndPhone;
            }
            return equalsMailOrPhone;
        } catch (NoResultException e) {
            try {
                TypedQuery<WebUser> querySec = em.createQuery("SELECT u FROM WebUser u WHERE u.phoneNumber = :Phone_Number", WebUser.class);
                querySec.setParameter("Phone_Number", phoneNum);
                WebUser userSec = querySec.getSingleResult();
                if(!(userSec == null)){
                    return equalsMailOrPhone;
                }
            } catch (NoResultException ex) {
                return equalsNotMailNotPhone;
            }
            return equalsNotMailNotPhone;
        } catch (Exception e) {
            e.printStackTrace();
            return otherError;
        } finally {
            em.close();
        }
    }

    public static synchronized boolean addUser(String firstName, String lastName, String email, String phone, boolean isManager) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            WebUser newUser = new WebUser();
            newUser.setWebUserId();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmailAddress(email);
            newUser.setPhoneNumber(phone);
            newUser.setIsManager(isManager);

            em.persist(newUser);
            et.commit();

            return true;
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public static List<String> getReviewsContentByArtist(String artistName) {
        EntityManager em = emf.createEntityManager();
        List<String> contentList = new ArrayList<>();
        try {
            TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r WHERE r.artistName.artistName = :name", Review.class);
            query.setParameter("name", artistName);
            List<Review> reviews = query.getResultList();
            for (Review review : reviews) {
                contentList.add(review.getContent());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return contentList;
    }

    public static synchronized boolean addReview(String content, String artistName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Artist artist = em.find(Artist.class, artistName);
            if (artist == null) {
                throw new IllegalArgumentException("Artist not found");
            }

            Review review = new Review();
            review.setContent(content);
            review.setArtistName(artist);
            review.setReviewId();

            em.persist(review);

            if (tx.getRollbackOnly()) {
                throw new Exception("Transaction failed.");
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public static synchronized boolean addPic(String artistName, String picName, byte[] fileBytes) throws IOException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Artist artist = em.find(Artist.class, artistName);
            if (artist == null) {
                throw new IllegalArgumentException("Artist not found");
            }

            Pictures picture = new Pictures();
            picture.setPicName(picName);
            picture.setContent(fileBytes);
            picture.setArtName(artist);

            em.persist(picture);

            // Check transaction
            if (tx.getRollbackOnly()) {
                throw new Exception("Transaction failed.");
            }

            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public static List<byte[]> getPicsByArtistName(String artistName) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p.content FROM Pictures p WHERE p.artName.artistName = :artistName");
            query.setParameter("artistName", artistName);
            query.setHint(QueryHints.REFRESH, true);

            List<byte[]> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                return Collections.singletonList(resultList.get(0));
            }
        } finally {
            em.close();
        }
        return null;
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}