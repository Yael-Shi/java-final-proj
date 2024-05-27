package utils;

public class ConcertInputs {
    private String artist;
    private String venue;
    private String date;
    private Integer unmarkedTickets;

    public ConcertInputs() {
        artist = "";
        venue = "";
        date = "";
        unmarkedTickets = 0;
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

    public Integer getUnmarkedTickets() {
        return unmarkedTickets;
    }

    public void setUnmarkedTickets(Integer unmarkedTickets) {
        this.unmarkedTickets = unmarkedTickets;
    }
}

