package beans;

import demo.db.Runner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name="userBean")
@ViewScoped
public class UserBean implements Serializable {
    @ManagedProperty(value = "#{menuBean}")
    protected MenuBean menuBean;
    @ManagedProperty(value = "#{orderBean}")
    protected OrderBean orderBean;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String userValidationMessage;
    private String bookingMessage;
    private Boolean sendMail;
    private String emailmsg;

    public UserBean() {
    }

    @PostConstruct
    public void init() {
        menuBean.setShowID(menuBean.showID);
        menuBean.setSelectedArtist(menuBean.selectedArtist);
        menuBean.setSelectedVenue(menuBean.selectedVenue);
        menuBean.setSelectedDate(menuBean.selectedDate);
        orderBean.setSelectedRow(orderBean.selectedRow);
        orderBean.setSelectedSeat(orderBean.selectedSeat);
        orderBean.setNumberOfSeatsToBuy(orderBean.numberOfSeatsToBuy);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public String getUserValidationMessage() {
        return userValidationMessage;
    }

    public void setUserValidationMessage(String userValidationMessage) {
        this.userValidationMessage = userValidationMessage;
    }

    public String getBookingMessage() {
        return bookingMessage;
    }

    public void setBookingMessage(String bookingMessage) {
        this.bookingMessage = bookingMessage;
    }

    public Boolean getSendMail() {
        return sendMail;
    }

    public void setSendMail(Boolean sendMail) {
        this.sendMail = sendMail;
    }

    public String getEmailmsg() {
        return emailmsg;
    }

    public void setEmailmsg(String emailmsg) {
        this.emailmsg = emailmsg;
    }

    public void submit() {
        Boolean userValid = false;
        Boolean doBook = false;
        Integer res = Runner.validateUser(email, phoneNumber);
        if (res == 0){
            userValid = true;
        }
        if (res == - 1){
            userValid = Runner.addUser(firstName, lastName, email, phoneNumber, false);
        } else if (res == 1) {
            userValidationMessage = null;
            userValidationMessage = "You already registered, but one or more of the details are incorrect. Please check and try again";
        }
        else if (res == 99 ){
            userValidationMessage = null;
            userValidationMessage = "An error has been occurred. Please try again in a few minutes";
        }
        if (userValid) {//just if user exists, add order to DB:
            userValidationMessage = null;
            bookingMessage = null;
            if (orderBean.selectedRow != 0) {
                doBook = Runner.makeMOrder(orderBean.selectedRow, orderBean.selectedSeat, menuBean.showID, email, phoneNumber);
            }
            else {
                doBook = Runner.makeUMOrder(orderBean.numberOfSeatsToBuy, menuBean.showID, email, phoneNumber);
            }
            if (doBook){
                bookingMessage = "Your order has been made successfully!"+
                        "would you like use to send you an email with the details?";
                sendMail = true;
            }
            else{
                bookingMessage = "An error has been occurred. Please try again in a few minutes." +
                        "If this error keep happening, please contact as at orders_sup@oursystem.com";
            }
        }
    }

    public void backToMainPage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

//    public void sendEmailToUsr() {
//        emailmsg = "Mail is being sending";
//        String host = "smtp.office365.com";
//        int port = 587;
//        String username = "orders_sup@walla.com";
//        String password = "JavaProject";
//        String from = "yael.080@gmail.com";
//        String to = "yael.080@gmail.com";
//        String subject = "User Registration Confirmation";
//        String messageContent = "Dear " + firstName + " " + lastName + ",\n\nThank you for registering on our platform.";
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
//            emailmsg = null;
//            emailmsg = "Email has been sent. Redirecting to main page";
//            Thread.sleep(5000);
//            backToMainPage();
//        } catch (EmailException e) {
//            e.printStackTrace();
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
}

