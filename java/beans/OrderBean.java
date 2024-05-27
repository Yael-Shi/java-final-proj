package beans;

import demo.db.Runner;
import demo.db.Tickets;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="orderBean")
@ViewScoped
public class OrderBean implements Serializable{
    @ManagedProperty(value = "#{menuBean}")
    protected MenuBean menuBean;
    private String message;
    private String errorMessage;
    private String seatType;
    protected static int numberOfSeatsToBuy;
    private boolean selectedMarked;
    private boolean selectedUnmarked;
    private int availableUnmarkedSeats;
    private int selectedMarkedSeat;
    boolean[] seatArray;
    boolean[][] seatMatrix;
    int[][] numberedSeats;
    protected static int selectedRow;
    protected static int selectedSeat;
    private String availabilityMessage;
    private Tickets allTicketsPerShow;

    public OrderBean(){
        selectedMarked = false;
        selectedUnmarked = false;
        seatType = "unmarked";

        //transfer into matrix
        try {
            allTicketsPerShow = Runner.getAllTickets(MenuBean.showID);
            seatArray = Runner.getAvailableMSeatsByTicket(allTicketsPerShow);
            seatMatrix = new boolean[5][5];
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    seatMatrix[row][col] = seatArray[row * 5 + col];
                }
            }
        } catch (IllegalAccessException e) {
            errorMessage = "An error occurred. Please try again in a few minutes." +
                    "If this error keep happening, please contact as at orders_sup@oursystem.com";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        availableUnmarkedSeats = Runner.getUMSeatsByTicket(allTicketsPerShow);
        menuBean.setShowID(menuBean.showID);
        menuBean.setSelectedArtist(menuBean.selectedArtist);
        menuBean.setSelectedVenue(menuBean.selectedVenue);
        menuBean.setSelectedDate(menuBean.selectedDate);
    }

    public String getMessage() {
        return message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getNumberOfSeatsToBuy() {
        return numberOfSeatsToBuy;
    }

    public void setNumberOfSeatsToBuy(int numberOfSeatsToBuy) {
        this.numberOfSeatsToBuy = numberOfSeatsToBuy;
    }

    public int getAvailableUnmarkedSeats() {
        return availableUnmarkedSeats;
    }

    public int[][] getNumberedSeats(){
        return numberedSeats;
    }

    public void setAvailableUnmarkedSeats(int availableUnmarkedSeats) {
        this.availableUnmarkedSeats = Runner.getUMSeatsByTicket(allTicketsPerShow);;
    }

    public boolean[] getMarkedSeats() {
        return seatArray;
    }

    public boolean[][] getSeatMatrix() {
        return seatMatrix;
    }

    public boolean getSelectedMarked() {
        return selectedMarked;
    }

    public boolean getSelectedUnmarked() {
        return selectedUnmarked;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedMarkedSeat(int selectedMarkedSeat) {
        this.selectedMarkedSeat = selectedMarkedSeat;
    }

    public int getSelectedMarkedSeat() {
        return selectedMarkedSeat;
    }

    public String getAvailabilityMessage() {
        return availabilityMessage;
    }

    public void setSelectedSeat(int selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
    }

    public String generateHtmlTable() {
        StringBuilder htmlTable = new StringBuilder();

        htmlTable.append("<table>");

        for (int row = 0; row < 5; row++) {
            htmlTable.append("<tr>");
            for (int col = 0; col < 5; col++) {
                String cssClass = seatMatrix[row][col] ? "trueSpot" : "falseSpot";
                htmlTable.append("<td class=\"" + cssClass + "\"></td>");
            }
            htmlTable.append("</tr>");
        }
        htmlTable.append("</table>");
        return htmlTable.toString();
    }

    public void printSeatType() {
        System.out.println(getSeatType());
    }

    public void validateUnmarkedSeats() {
        if (numberOfSeatsToBuy > availableUnmarkedSeats) {
            numberOfSeatsToBuy = 0;
            errorMessage = ("There are only " +availableUnmarkedSeats +" unmarked seats available. Choose a different amount.");}
        else selectedUnmarked = true;
    }

    public String printAvailableSeats() {
        StringBuilder sb = new StringBuilder();
        sb.append("<br/>");
        int rowNum = 1;
        for (boolean[] row : seatMatrix) {
            sb.append("Row ").append(rowNum).append(": ");
            for (int i = 0; i < row.length; i++) {
                if (row[i]) {
                    sb.append(i+1).append(" ");
                }
            }
            sb.append("<br/>");
            rowNum++;
        }
        return sb.toString();
    }

    public void checkAvailability() {
        if (selectedRow < 1 || selectedRow > seatMatrix.length) {
            availabilityMessage = null;
            availabilityMessage = "Invalid row number. Please enter a number between 1 and " + seatMatrix.length + ".";
        } else if (selectedSeat < 1 || selectedSeat > seatMatrix[0].length) {
            availabilityMessage = null;
            availabilityMessage = "Invalid seat number. Please enter a number between 1 and " + seatMatrix[0].length + " for row " + selectedRow + ".";
        } else if (!seatMatrix[selectedRow - 1][selectedSeat - 1]) {
            availabilityMessage = "Seat " + selectedRow + "-" + selectedSeat + " is not available.";
        } else {
            availabilityMessage = null;
            availabilityMessage = "Seat " + selectedRow + "-" + selectedSeat + " is available!";
            selectedMarkedSeat = getSeatNumber(selectedRow, selectedSeat);
            selectedMarked = true;
        }
    }

    public int getSeatNumber(int row, int seat) {
        return (row - 1) * 5 + seat;
    }

    public void confirm() throws IOException {
        validateUnmarkedSeats();
        String columnName = "seat" + selectedRow + selectedSeat;
        if(!(Runner.isSeatAvailable(columnName, MenuBean.showID))){
            String infoMessage = "This seat is already taken";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, infoMessage, null));
            throw new IllegalStateException("Seat is already taken");
        }
        if ((selectedUnmarked == true) || (selectedMarked == true))  {
            FacesContext.getCurrentInstance().getExternalContext().redirect("userDetails.xhtml");
        }
    }
}
