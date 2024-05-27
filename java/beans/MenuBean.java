package beans;

import utils.DateTimeConvertor;
import demo.db.Runner;
import demo.db.Show;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean implements Serializable {
    protected static String selectedArtist;
    protected List<String> artistItems;
    protected List<String> venueItems;
    protected static String selectedVenue;
    private List<String> dateItems;
    protected static String selectedDate;
    protected static String showID;
    protected List<Show> showsByArtist;
    protected String imagePath;
    protected String selectedArtistReview;
    protected String review;
    protected List<String> allReviews;
    protected String errorMessage = "An error occurred. Please try again in a few minutes." +
            "If this error keep happening, please contact as at orders_sup@oursystem.com";

    public MenuBean() {
        try {
            artistItems = Runner.getAllArtistNames();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
            throw new RuntimeException(e);
        }
        venueItems = new ArrayList<>();
        dateItems = new ArrayList<>();
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return this.selectedArtist +".jpg";
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

    public String getSelectedArtistReview() {
        return selectedArtistReview;
    }

    public void setSelectedArtistReview(String selectedArtistReview) {
        this.selectedArtistReview = selectedArtistReview;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<String> getAllReviews() {
        return allReviews;
    }

    public void updateVenueList(AjaxBehaviorEvent event) {
        try {
            showsByArtist = Runner.getShowsByArtist(getSelectedArtist());

            try {
                allReviews.clear();

            } catch (Exception e){
//                if there is no review, keep running, it's ok.
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
            throw new RuntimeException(errorMessage);
        }
        updateReviewsByArtist();
        venueItems.clear();
        selectedVenue = null;
        venueItems = extractVenues();
    }

    public void updateDateList(AjaxBehaviorEvent event) {
        dateItems.clear();
        selectedDate = null;
        dateItems = extractDates();
    }

    public void submitReview() {
        Runner.addReview(review, selectedArtistReview);
    }

    //function that updates the ReviewList when a new artist is selected
    public void updateReviewsByArtist() {
        allReviews = Runner.getReviewsContentByArtist(selectedArtist);
    }

    public void selectConcert() throws IOException {
        //redirect to new page
        FacesContext.getCurrentInstance().getExternalContext().redirect("order.xhtml");
        showID = extractChosenShowId();
    }

}
