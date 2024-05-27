package beans;

import utils.ConcertInputs;
import utils.DateTimeConvertor;
import demo.db.Runner;
import demo.db.Show;
import utils.ItemAlreadyExistsException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean implements Serializable {
    private String username;
    private String password;
    private String artist;
    private String venue;
    private String date;
    private List<Show> concerts = new ArrayList<>();
    private List<ConcertInputs> concertInputsList  = new ArrayList<>();
    private boolean loggedIn;
    private Part image;
    protected static String selectedArtist;
    protected List<String> artistItems;
    protected List<String> venueItems;
    protected static String selectedVenue;
    private List<String> dateItems;
    protected static String selectedDate;
    protected static String showID;
    protected List<Show> showsByArtist;
    private List<String[]> requestedRes;
    private String concertTable;
    protected String errorMessage = "An error occurred. Please try again in a few minutes." +
            "If this error keep happening, please contact as at orders_sup@oursystem.com";

    public AdminBean(){
        try {
            artistItems = Runner.getAllArtistNames();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
            throw new RuntimeException(e);
        }
        venueItems = new ArrayList<>();
        dateItems = new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Show> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Show> concerts) {
        this.concerts = concerts;
    }

    public List<String[]> getRequestedRes() {
        return requestedRes;
    }

    public void setRequestedRes(List<String[]> requestedRes) {
        this.requestedRes = requestedRes;
    }

    public void addNewShow() {
        concertInputsList.add(new ConcertInputs());
    }

    public void addNewArtist() {
        concertInputsList.add(new ConcertInputs());
    }

    public void addNewVenue() {
        concertInputsList.add(new ConcertInputs());
    }

    public void addNewDate() {
        concertInputsList.add(new ConcertInputs());
    }

    public void addNewUMTickets() {
        concertInputsList.add(new ConcertInputs());
    }

    public List<ConcertInputs> getConcertInputsList() {
        return concertInputsList;
    }

    public void setConcertInputsList(List<ConcertInputs> concertInputsList) {
        this.concertInputsList = concertInputsList;
    }

    public void submitShows() {
        try {
            for (ConcertInputs ci : concertInputsList) {
                DateTimeConvertor conv = new DateTimeConvertor(ci.getDate());
                Runner.addShow(ci.getArtist(), ci.getVenue(), conv.getTime(), conv.getDate(), ci.getUnmarkedTickets());
            }
        } catch (ItemAlreadyExistsException e){
            String infoMsg = "Item already exists";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, infoMsg, null));
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
            throw new RuntimeException(e);
        }
    }

    public void viewConcerts() {
        // Code to view concerts
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public String getConcertTable() {
        return concertTable;
    }

    public void setConcertTable(String concertTable) {
        this.concertTable = concertTable;
    }

    public void login() throws IOException {
        if (username.equals("admin") && password.equals("password")) {
            loggedIn = true;
            FacesContext.getCurrentInstance().getExternalContext().redirect("adminSettings.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid username or password"));

        }
    }
    public void upload() throws IOException {
        if (image != null) {
            InputStream fileContent = image.getInputStream();
            byte[] fileBytes = readBytesFromInputStream(fileContent);
            String newFileName = "new_filename.jpg"; // Set new file name

            Runner.addPic(selectedArtist, newFileName, fileBytes);
        }
    }

    private byte[] readBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, length);
        }
        return output.toByteArray();
    }

    private List<String> extractVenues() {
        Set<String> uniqueVenues = new HashSet<>();
        for (Show show : showsByArtist) {
            uniqueVenues.add(show.getShowLocation());
        }
        return new ArrayList<>(uniqueVenues);
    }

    private List<String> extractDates() {
        List<String> dates = new ArrayList<>();
        for (Show show : showsByArtist) {
            if (show.getShowLocation().equals(this.selectedVenue) && show.getArtName().getArtistName().equals(this.selectedArtist)) {
                DateTimeConvertor conv = new DateTimeConvertor(show.getShowDate(), show.getShowTime());
                dates.add(conv.getDateTimeSrt());
            }
        }
        return dates;
    }

    private String extractChosenShowId(){
        for (Show show : showsByArtist) {
            DateTimeConvertor conv = new DateTimeConvertor(show.getShowDate(), show.getShowTime());
            if (show.getShowLocation().equals(this.selectedVenue) &&
                    show.getArtName().getArtistName().equals(this.selectedArtist) &&
                    conv.getDateTimeSrt().equals(this.selectedDate)) {
                return show.getShowId();
            }
        }
        return null;
    }

    public String getSelectedArtist() {
        return selectedArtist;
    }

    public void setSelectedArtist(String selectedOption) {
        this.selectedArtist = selectedOption;
    }

    public List<String> getArtistItems() {
        return artistItems;
    }

    public List<Show> getShowsByArtist() {
        return showsByArtist;
    }

    public String getSelectedVenue() {
        return selectedVenue;
    }

    public void setSelectedVenue(String selectedVenue) {
        this.selectedVenue = selectedVenue;
    }

    public List<String> getVenueItems() {
        return venueItems;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<String> getDateItems() {
        return dateItems;
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID){
        this.showID = showID;
    }

    public void updateVenueList(AjaxBehaviorEvent event) {
        try {
            showsByArtist = Runner.getShowsByArtist(getSelectedArtist());
        } catch (Exception e) {
            String errorMessage = "An error occurred while loading artist names. Please try again later.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
            throw new RuntimeException(e);
        }
        venueItems.clear();
        selectedVenue = null;
        venueItems = extractVenues();
    }

    public void updateDateList(AjaxBehaviorEvent event) {
        dateItems.clear();
//        selectedDate = null;
        dateItems = extractDates();
    }

    public String createConcertTable(List<String[]> stringList) {
        StringBuilder htmlTable = new StringBuilder();
        // Add the table headers
        htmlTable.append("<table><thead><tr><th>First Name</th><th>Last Name</th><th>Email</th><th>Phone Number</th><th>Tickets</th></tr></thead><tbody>");

        // Add the table rows
        for (String[] strArray : stringList) {
            htmlTable.append("<tr>");
            for (String str : strArray) {
                htmlTable.append("<td>").append(str).append("</td>");
            }
            htmlTable.append("</tr>");
        }
        htmlTable.append("</tbody></table>");

        return htmlTable.toString();
    }

    public void displayOrders() throws IOException {
        requestedRes = Runner.getOrdersByArtistAndShow(selectedArtist, selectedVenue);
        concertTable = createConcertTable(requestedRes);
    }

}
