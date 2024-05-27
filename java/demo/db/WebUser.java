package demo.db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Web_user")
@NamedQueries({
    @NamedQuery(name = "WebUser.findAll", query = "SELECT w FROM WebUser w"),
    @NamedQuery(name = "WebUser.findByWebUserId", query = "SELECT w FROM WebUser w WHERE w.webUserId = :webUserId"),
    @NamedQuery(name = "WebUser.findByFirstName", query = "SELECT w FROM WebUser w WHERE w.firstName = :firstName"),
    @NamedQuery(name = "WebUser.findByLastName", query = "SELECT w FROM WebUser w WHERE w.lastName = :lastName"),
    @NamedQuery(name = "WebUser.findByEmailAddress", query = "SELECT w FROM WebUser w WHERE w.emailAddress = :emailAddress"),
    @NamedQuery(name = "WebUser.findByPhoneNumber", query = "SELECT w FROM WebUser w WHERE w.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "WebUser.findByIsManager", query = "SELECT w FROM WebUser w WHERE w.isManager = :isManager")})
public class WebUser implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "webUserId")
    private Collection<Orders> ordersCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "web_user_id")
    private String webUserId = Runner.generateUUID();
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_address")
    private String emailAddress;
    @Basic(optional = false)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "is_manager")
    private Boolean isManager;

    public WebUser() {
    }

    public WebUser(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebUserId() {
        return webUserId;
    }

    public void setWebUserId() {
        this.webUserId = Runner.generateUUID();
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (webUserId != null ? webUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebUser)) {
            return false;
        }
        WebUser other = (WebUser) object;
        if ((this.webUserId == null && other.webUserId != null) || (this.webUserId != null && !this.webUserId.equals(other.webUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.db.WebUser[ webUserId=" + webUserId + " ]";
    }

    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }
}
