package demo.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
@NamedQueries({
        @NamedQuery(name = "Pictures.findAll", query = "SELECT p FROM Pictures p"),
        @NamedQuery(name = "Pictures.findByPicId", query = "SELECT p FROM Pictures p WHERE p.picId = :picId")})
public class Pictures implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pic_id")
    private String picId;
    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "pic_name")
    private String picName;

    @JoinColumn(name = "art_name", referencedColumnName = "artist_name")
    @ManyToOne(optional = false)
    private Artist artName;

    public Pictures() {
        this.picId = Runner.generateUUID();;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId() {
        this.picId = Runner.generateUUID();;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Artist getArtName() {
        return artName;
    }

    public void setArtName(Artist artName) {
        this.artName = artName;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (picId != null ? picId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pictures)) {
            return false;
        }
        Pictures other = (Pictures) object;
        if ((this.picId == null && other.picId != null) || (this.picId != null && !this.picId.equals(other.picId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.db.Pictures[ picId=" + picId + " ]";
    }
}
