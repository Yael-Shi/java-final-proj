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
@Table(name = "artist")
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a"),
    @NamedQuery(name = "Artist.findByArtistId", query = "SELECT a FROM Artist a WHERE a.artistId = :artistId"),
    @NamedQuery(name = "Artist.findByArtistName", query = "SELECT a FROM Artist a WHERE a.artistName = :artistName")})
public class Artist implements Serializable {

    @OneToMany(mappedBy = "artistName")
    private Collection<Review> reviewCollection;

    private static final long serialVersionUID = 1L;
    @Column(name = "artist_id")
    private String artistId = Runner.generateUUID();
    @Id
    @Basic(optional = false)
    @Column(name = "artist_name")
    private String artistName;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artName")
    private Collection<Show> showCollection;

    public Artist() {
    }

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId() {
        this.artistId = Runner.generateUUID();
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Collection<Show> getShowCollection() {
        return showCollection;
    }

    public void setShowCollection(Collection<Show> showCollection) {
        this.showCollection = showCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artistName != null ? artistName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.artistName == null && other.artistName != null) || (this.artistName != null && !this.artistName.equals(other.artistName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.db.Artist[ artistName=" + artistName + " ]";
    }

    public Collection<Review> getReviewCollection() {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<Review> reviewCollection) {
        this.reviewCollection = reviewCollection;
    }
}
