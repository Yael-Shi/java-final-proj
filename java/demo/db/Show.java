/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.db;

import java.io.Serializable;
import java.util.Collection;
import java.sql.Date;
import java.sql.Time;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "show")
@NamedQueries({
    @NamedQuery(name = "Show.findAll", query = "SELECT s FROM Show s"),
    @NamedQuery(name = "Show.findByShowId", query = "SELECT s FROM Show s WHERE s.showId = :showId"),
    @NamedQuery(name = "Show.findByShowTime", query = "SELECT s FROM Show s WHERE s.showTime = :showTime"),
    @NamedQuery(name = "Show.findByShowLocation", query = "SELECT s FROM Show s WHERE s.showLocation = :showLocation")})
public class Show implements Serializable {

    @Column(name = "show_date")
//    @Temporal(TemporalType.DATE)
    private Date showDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "showId")
    private Collection<Tickets> ticketsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "showId")
    private Collection<Orders> ordersCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "show_id")
    private String showId = Runner.generateUUID();
    
    @Column(name = "show_time")
    private Time showTime;
    
    @Basic(optional = false)
    @Column(name = "show_location")
    private String showLocation;
    
    @JoinColumn(name = "art_name", referencedColumnName = "artist_name")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Art_Name")
    private Artist artName;

    public Show() {
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId() {
        this.showId = Runner.generateUUID();
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

    public String getShowLocation() {
        return showLocation;
    }

    public void setShowLocation(String showLocation) {
        this.showLocation = showLocation;
    }

    public Artist getArtName() {
        return artName;
    }

    public void setArtName(Artist artName) {
        this.artName = artName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (showId != null ? showId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Show)) {
            return false;
        }
        Show other = (Show) object;
        if ((this.showId == null && other.showId != null) || (this.showId != null && !this.showId.equals(other.showId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.db.Show[ showId=" + showId + " ]";
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Collection<Tickets> getTicketsCollection() {
        return ticketsCollection;
    }

    public void setTicketsCollection(Collection<Tickets> ticketsCollection) {
        this.ticketsCollection = ticketsCollection;
    }

    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }
    
    
    
}
