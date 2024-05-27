/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author yael0
 */
@Entity
@Table(name = "tickets")
@NamedQueries({
    @NamedQuery(name = "Tickets.findAll", query = "SELECT t FROM Tickets t"),
    @NamedQuery(name = "Tickets.findByTicketsId", query = "SELECT t FROM Tickets t WHERE t.ticketsId = :ticketsId"),
    @NamedQuery(name = "Tickets.findByUnmarked", query = "SELECT t FROM Tickets t WHERE t.UnmarkedSeats = :UnmarkedSeats"),
    @NamedQuery(name = "Tickets.findByUnmarkedLeft", query = "SELECT t FROM Tickets t WHERE t.unmarkedLeft = :unmarkedLeft"),
    @NamedQuery(name = "Tickets.findBySeat11", query = "SELECT t FROM Tickets t WHERE t.seat11 = :seat11"),
    @NamedQuery(name = "Tickets.findBySeat12", query = "SELECT t FROM Tickets t WHERE t.seat12 = :seat12"),
    @NamedQuery(name = "Tickets.findBySeat13", query = "SELECT t FROM Tickets t WHERE t.seat13 = :seat13"),
    @NamedQuery(name = "Tickets.findBySeat14", query = "SELECT t FROM Tickets t WHERE t.seat14 = :seat14"),
    @NamedQuery(name = "Tickets.findBySeat15", query = "SELECT t FROM Tickets t WHERE t.seat15 = :seat15"),
    @NamedQuery(name = "Tickets.findBySeat21", query = "SELECT t FROM Tickets t WHERE t.seat21 = :seat21"),
    @NamedQuery(name = "Tickets.findBySeat22", query = "SELECT t FROM Tickets t WHERE t.seat22 = :seat22"),
    @NamedQuery(name = "Tickets.findBySeat23", query = "SELECT t FROM Tickets t WHERE t.seat23 = :seat23"),
    @NamedQuery(name = "Tickets.findBySeat24", query = "SELECT t FROM Tickets t WHERE t.seat24 = :seat24"),
    @NamedQuery(name = "Tickets.findBySeat25", query = "SELECT t FROM Tickets t WHERE t.seat25 = :seat25"),
    @NamedQuery(name = "Tickets.findBySeat31", query = "SELECT t FROM Tickets t WHERE t.seat31 = :seat31"),
    @NamedQuery(name = "Tickets.findBySeat32", query = "SELECT t FROM Tickets t WHERE t.seat32 = :seat32"),
    @NamedQuery(name = "Tickets.findBySeat33", query = "SELECT t FROM Tickets t WHERE t.seat33 = :seat33"),
    @NamedQuery(name = "Tickets.findBySeat34", query = "SELECT t FROM Tickets t WHERE t.seat34 = :seat34"),
    @NamedQuery(name = "Tickets.findBySeat35", query = "SELECT t FROM Tickets t WHERE t.seat35 = :seat35"),
    @NamedQuery(name = "Tickets.findBySeat41", query = "SELECT t FROM Tickets t WHERE t.seat41 = :seat41"),
    @NamedQuery(name = "Tickets.findBySeat42", query = "SELECT t FROM Tickets t WHERE t.seat42 = :seat42"),
    @NamedQuery(name = "Tickets.findBySeat43", query = "SELECT t FROM Tickets t WHERE t.seat43 = :seat43"),
    @NamedQuery(name = "Tickets.findBySeat44", query = "SELECT t FROM Tickets t WHERE t.seat44 = :seat44"),
    @NamedQuery(name = "Tickets.findBySeat45", query = "SELECT t FROM Tickets t WHERE t.seat45 = :seat45"),
    @NamedQuery(name = "Tickets.findBySeat51", query = "SELECT t FROM Tickets t WHERE t.seat51 = :seat51"),
    @NamedQuery(name = "Tickets.findBySeat52", query = "SELECT t FROM Tickets t WHERE t.seat52 = :seat52"),
    @NamedQuery(name = "Tickets.findBySeat53", query = "SELECT t FROM Tickets t WHERE t.seat53 = :seat53"),
    @NamedQuery(name = "Tickets.findBySeat54", query = "SELECT t FROM Tickets t WHERE t.seat54 = :seat54"),
    @NamedQuery(name = "Tickets.findBySeat55", query = "SELECT t FROM Tickets t WHERE t.seat55 = :seat55")})
public class Tickets implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketsId")
    private Collection<Orders> ordersCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tickets_id")
    private String ticketsId = Runner.generateUUID();
    @Column(name = "Unmarked_Seats")
    private Integer UnmarkedSeats;
    @Column(name = "unmarked_left")
    private Integer unmarkedLeft;
    @Column(name = "seat1_1")
    private Boolean seat11;
    @Column(name = "seat1_2")
    private Boolean seat12;
    @Column(name = "seat1_3")
    private Boolean seat13;
    @Column(name = "seat1_4")
    private Boolean seat14;
    @Column(name = "seat1_5")
    private Boolean seat15;
    @Column(name = "seat2_1")
    private Boolean seat21;
    @Column(name = "seat2_2")
    private Boolean seat22;
    @Column(name = "seat2_3")
    private Boolean seat23;
    @Column(name = "seat2_4")
    private Boolean seat24;
    @Column(name = "seat2_5")
    private Boolean seat25;
    @Column(name = "seat3_1")
    private Boolean seat31;
    @Column(name = "seat3_2")
    private Boolean seat32;
    @Column(name = "seat3_3")
    private Boolean seat33;
    @Column(name = "seat3_4")
    private Boolean seat34;
    @Column(name = "seat3_5")
    private Boolean seat35;
    @Column(name = "seat4_1")
    private Boolean seat41;
    @Column(name = "seat4_2")
    private Boolean seat42;
    @Column(name = "seat4_3")
    private Boolean seat43;
    @Column(name = "seat4_4")
    private Boolean seat44;
    @Column(name = "seat4_5")
    private Boolean seat45;
    @Column(name = "seat5_1")
    private Boolean seat51;
    @Column(name = "seat5_2")
    private Boolean seat52;
    @Column(name = "seat5_3")
    private Boolean seat53;
    @Column(name = "seat5_4")
    private Boolean seat54;
    @Column(name = "seat5_5")
    private Boolean seat55;
    
    @JoinColumn(name = "show_id", referencedColumnName = "show_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Show_ID")
    private Show showId;

    public Tickets() {
    }

    public String getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId() {
        this.ticketsId = Runner.generateUUID();
    }

    public Integer getUnmarkedSeats() {
        return UnmarkedSeats;
    }

    public void setUnmarkedSeats(Integer unmarked) {
        this.UnmarkedSeats = unmarked;
    }

    public Integer getUnmarkedLeft() {
        return unmarkedLeft;
    }

    public void setUnmarkedLeft(Integer unmarkedLeft) {
        this.unmarkedLeft = unmarkedLeft;
    }

    public Boolean getSeat11() {
        return seat11;
    }

    public void setSeat11(Boolean seat11) {
        this.seat11 = seat11;
    }

    public Boolean getSeat12() {
        return seat12;
    }

    public void setSeat12(Boolean seat12) {
        this.seat12 = seat12;
    }

    public Boolean getSeat13() {
        return seat13;
    }

    public void setSeat13(Boolean seat13) {
        this.seat13 = seat13;
    }

    public Boolean getSeat14() {
        return seat14;
    }

    public void setSeat14(Boolean seat14) {
        this.seat14 = seat14;
    }

    public Boolean getSeat15() {
        return seat15;
    }

    public void setSeat15(Boolean seat15) {
        this.seat15 = seat15;
    }

    public Boolean getSeat21() {
        return seat21;
    }

    public void setSeat21(Boolean seat21) {
        this.seat21 = seat21;
    }

    public Boolean getSeat22() {
        return seat22;
    }

    public void setSeat22(Boolean seat22) {
        this.seat22 = seat22;
    }

    public Boolean getSeat23() {
        return seat23;
    }

    public void setSeat23(Boolean seat23) {
        this.seat23 = seat23;
    }

    public Boolean getSeat24() {
        return seat24;
    }

    public void setSeat24(Boolean seat24) {
        this.seat24 = seat24;
    }

    public Boolean getSeat25() {
        return seat25;
    }

    public void setSeat25(Boolean seat25) {
        this.seat25 = seat25;
    }

    public Boolean getSeat31() {
        return seat31;
    }

    public void setSeat31(Boolean seat31) {
        this.seat31 = seat31;
    }

    public Boolean getSeat32() {
        return seat32;
    }

    public void setSeat32(Boolean seat32) {
        this.seat32 = seat32;
    }

    public Boolean getSeat33() {
        return seat33;
    }

    public void setSeat33(Boolean seat33) {
        this.seat33 = seat33;
    }

    public Boolean getSeat34() {
        return seat34;
    }

    public void setSeat34(Boolean seat34) {
        this.seat34 = seat34;
    }

    public Boolean getSeat35() {
        return seat35;
    }

    public void setSeat35(Boolean seat35) {
        this.seat35 = seat35;
    }

    public Boolean getSeat41() {
        return seat41;
    }

    public void setSeat41(Boolean seat41) {
        this.seat41 = seat41;
    }

    public Boolean getSeat42() {
        return seat42;
    }

    public void setSeat42(Boolean seat42) {
        this.seat42 = seat42;
    }

    public Boolean getSeat43() {
        return seat43;
    }

    public void setSeat43(Boolean seat43) {
        this.seat43 = seat43;
    }

    public Boolean getSeat44() {
        return seat44;
    }

    public void setSeat44(Boolean seat44) {
        this.seat44 = seat44;
    }

    public Boolean getSeat45() {
        return seat45;
    }

    public void setSeat45(Boolean seat45) {
        this.seat45 = seat45;
    }

    public Boolean getSeat51() {
        return seat51;
    }

    public void setSeat51(Boolean seat51) {
        this.seat51 = seat51;
    }

    public Boolean getSeat52() {
        return seat52;
    }

    public void setSeat52(Boolean seat52) {
        this.seat52 = seat52;
    }

    public Boolean getSeat53() {
        return seat53;
    }

    public void setSeat53(Boolean seat53) {
        this.seat53 = seat53;
    }

    public Boolean getSeat54() {
        return seat54;
    }

    public void setSeat54(Boolean seat54) {
        this.seat54 = seat54;
    }

    public Boolean getSeat55() {
        return seat55;
    }

    public void setSeat55(Boolean seat55) {
        this.seat55 = seat55;
    }

    public Show getShowId() {
        return showId;
    }

    public void setShowId(Show showId) {
        this.showId = showId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketsId != null ? ticketsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tickets)) {
            return false;
        }
        Tickets other = (Tickets) object;
        if ((this.ticketsId == null && other.ticketsId != null) || (this.ticketsId != null && !this.ticketsId.equals(other.ticketsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.db.Tickets[ ticketsId=" + ticketsId + " ]";
    }

    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }
}
