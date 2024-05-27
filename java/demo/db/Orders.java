package demo.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrderId", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Orders.findByWhichTicket", query = "SELECT o FROM Orders o WHERE o.whichTicket = :whichTicket")})
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "order_id")
    private String orderId = Runner.generateUUID();
    @Basic(optional = false)
    @Column(name = "which_ticket")
    private String whichTicket;
    @JoinColumn(name = "show_id", referencedColumnName = "show_id")
    @ManyToOne(optional = false)
    private Show showId;
    @JoinColumn(name = "tickets_id", referencedColumnName = "tickets_id")
    @ManyToOne(optional = false)
    private Tickets ticketsId;
    @JoinColumn(name = "web_user_id", referencedColumnName = "web_user_id")
    @ManyToOne(optional = false)
    private WebUser webUserId;

    public Orders() {
    }

    public Orders(String whichTicket) {
        this.whichTicket = whichTicket;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId() {
        this.orderId = Runner.generateUUID();
    }

    public String getWhichTicket() {
        return whichTicket;
    }

    public void setWhichTicket(String whichTicket) {
        this.whichTicket = whichTicket;
    }

    public Show getShowId() {
        return showId;
    }

    public void setShowId(Show showId) {
        this.showId = showId;
    }

    public Tickets getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId(Tickets ticketsId) {
        this.ticketsId = ticketsId;
    }

    public WebUser getWebUserId() {
        return webUserId;
    }

    public void setWebUserId(WebUser webUserId) {
        this.webUserId = webUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return orderId;
    }    
}
