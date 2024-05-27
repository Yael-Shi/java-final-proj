package demo.db;

import utils.DateTimeConvertor;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public class DB {

    public static void main(String[] args) throws ParseException, IOException {
        List<String> some = Runner.getReviewsContentByArtist("Neta");
//        DateTimeConvertor conv = new DateTimeConvertor("2023-12-12 20:00:00");

//        List<String[]> some = Runner.getOrdersByArtistAndShow("Neta", "Beer-Sheva", conv.getDate(), conv.getTime());
        System.out.println(some);
//        DateTimeConvertor conv = new DateTimeConvertor("2023-10-15 20:30:00");
//        Runner.addShow("Neta", "TLV", conv.getTime(), conv.getDate(), 37);

        }

//        sendEmailToUsr();
//    }
//    public static void sendEmailToUsr() {
//        String host = "smtp.office365.com";
//        int port = 587;
//        String username = "orders_sup@walla.com";
//        String password = "JavaProject";
//        String from = "orders_sup@walla.com";
//        String to = "yael.080@gmail.com";
//        String subject = "User Registration Confirmation";
//        String messageContent = "Dear yael s" + ",\n\nThank you for registering on our platform.";
//
//        try {
//            Email email = new SimpleEmail();
//            email.setHostName(host);
//            email.setSmtpPort(port);
//            email.setAuthenticator(new DefaultAuthenticator(username, password));
//            email.setTLS(true);
//            email.setFrom(from);
//            email.setSubject(subject);
//            email.setMsg(messageContent);
//            email.addTo(to);
//            email.send();
////            Thread.sleep(5000);
//        } catch (EmailException e) {
//            e.printStackTrace();
//        }
//    }

//        Boolean something = Runner.addReview("really nice!", "1a", "Dylan");
//        System.out.println(something);

//        WebUser something = Runner.getUserByMailPhone("safsafa","0123456789");
//        Boolean something = Runner.addUser("a","b", "c", "d", false);
//        System.out.println(something);
//        String email = "@@";
//        String phoneNumber = "9";
//        String firstName = "aaa";
//        String lastName = "bbb";
//        Boolean userValid = false;
//        Boolean doBook = false;
//
//        Integer res = Runner.validateUser(email, phoneNumber);
//        System.out.println(res);
//        if (res == 0){
//            System.out.println("res=0");
//        }
//        if (res == - 1){
//            userValid = Runner.addUser(firstName, lastName, email, phoneNumber, false);
//            System.out.println("userValid " + userValid);
//        } else if (res == 1) {
//            System.out.println("res=1");
//        } else if (res == 99 ){
//            System.out.println("res=99");
//        }

//        if (userValid) {//just if user exists, add order to DB:
//            if (orderBean.selectedRow != 0) {
//                doBook = Runner.makeMOrder(orderBean.selectedRow, orderBean.selectedSeat, menuBean.showID, email, phoneNumber);
//            }
//            else {
//                Boolean doBook = Runner.makeMOrder(2, 2,"16a", "@@", "090");
//                System.out.println(doBook);
////            }
//            if (doBook){
//                sendEmail();
//            }
//            else{
//                //error msg to try again within a few mins.
//            }
        }




//        Tickets ticket = Runner.getTicketByShowID("16a");
//        System.out.println(Runner.getUMSeatsByTicket(ticket));
//        boolean[] myArray = Runner.getAvailableMSeatsByTicket(ticket);
//
//        for (int i = 0; i < myArray.length; i++) {
//            System.out.println(myArray[i]);
//        }
//        System.out.println(Runner.getAvailableMSeatsByTicket(ticket));



//    }
//        List<Show> sh = Runner.getShowsByArtist("Dylan");
//        List<String> something = extractVenues(sh);
//        System.out.println(something);
//    }
//
//
//    private static List<String> extractVenues(List<Show> sh) {
//        List<String> venues = new ArrayList<>();
//        for (Show show : sh) {
//            venues.add(show.getShowLocation());
//        }
//        return venues;
//    }
//}
//        Boolean something = Runner.addPic("Dylan", "C:\\Users\\yael0\\Desktop\\yael\\demo\\IMG_3725.JPG");
//        System.out.println(something);
//        List<byte[]> somethingelse = Runner.getPicsByArtistName("Dylan");
//        System.out.println(somethingelse);
//        List<Show> showslist = Runner.getShowsByArtist("Dylan");
//        System.out.println(showslist);

//        String something = dateTimeToString();
//        System.out.println(something);
//        // get all artists names
//        List<String> artistNames = Runner.getAllArtistNames();
//        System.out.println("All artist names:");
//        artistNames.forEach(System.out::println);
//
        // get shows by artist name
//        List<String> showid = new ArrayList<>();
//        List<String> shows = Runner.getShowsLocationsByArtist("Dylan");
//        System.out.println(shows);

//        List<Boolean> av = Runner.getAvailableSeatsByShow("16a");
//        System.out.println(av);
//        for (Show show : shows) {
//            System.out.println("Show ID: " + show.getShowId());
//            showid.add(show.getShowId());
//            System.out.println("Show Time: " + show.getShowTime());
//            System.out.println("Show Location: " + show.getShowLocation());
//            System.out.println("Artist Name: " + show.getArtName().getArtistName());
//            System.out.println("------------------------------");
//        }
//        // get showid by other detailes
//        String artistName = "Dylan";
//        Date showDate = Date.valueOf("2023-04-15");
//        Time showTime = Time.valueOf("20:00:00");
//        String showId = Runner.getShowIdByCriteria(artistName, showDate, showTime);
//        System.out.println("The Show ID is: " + showId);
//
        // get avaliable tickets by showid
//        List<Tickets> availableTickets = Runner.getAvailableTicketsByShow("16a");
//        try {
//            for (Tickets ticket : availableTickets) {
//                for (Field field : Tickets.class.getDeclaredFields()) {
//                    field.setAccessible(true);
//                    String fieldName = field.getName();
//                    Object fieldValue = field.get(ticket);
//                    if ((fieldValue instanceof Boolean) && fieldName.startsWith("seat") && fieldValue.equals(true)){
//                        //do something
//                        System.out.println("+++++++++++++++++++++++++++++++++++++");
//                            System.out.println(fieldName + "=" + fieldValue);
//                            System.out.println("Column name: " + fieldName + ", value: " + fieldValue);
//                            System.out.println("+++++++++++++++++++++++++++++++++++++");
//                    }
////                     need to add here return of unmarked seat
//                }
//            }
//        } catch (IllegalAccessException e) {
//            System.out.println(e);
//        }
//        Runner.bookedSeat(5,1, "16");
////         getting orders by a parameter
//        List<Orders> findOrdersByShowId = Runner.findOrdersByShowId("16a");
//        System.out.println(findOrdersByShowId);
//        List<Orders> findOrdersByUserId = Runner.findOrdersByUserId("1a");
//    }
//    public static String dateTimeToString(java.sql.Date showDate, java.sql.Time showTime) {
////        Date showDate = Date.valueOf("2023-04-15");
////        Date showDate = Date.valueOf(date);
////        Time showTime = Time.valueOf("20:00:00");
////        Time showTime = Time.valueOf(time);
//        DateFormat formatterD = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat formatterT = new SimpleDateFormat("HH:mm:ss");
//        String formattedDateTime = formatterD.format(showDate) + " " + formatterT.format(showTime);
//        return formattedDateTime;
//    }
//}


//        
//        System.out.println(showid);
//        makeSeatBooked(2, 3, showID); // Mark seat 2,3 as unavailable

//    }
