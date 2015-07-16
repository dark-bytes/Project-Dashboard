/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SUDHANSHU
 */
@Entity
@Table(name = "mission_id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MissionId.findAll", query = "SELECT m FROM MissionId m"),
    @NamedQuery(name = "MissionId.findById", query = "SELECT m FROM MissionId m WHERE m.id = :id"),
    @NamedQuery(name = "MissionId.findByMissionInfo", query = "SELECT m FROM MissionId m WHERE m.missionInfo = :missionInfo")})
public class MissionId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Expose
    @Size(min = 1, max = 200)
    @Column(name = "mission_info")
    private String missionInfo;
    @OneToMany(mappedBy = "missionId",orphanRemoval = true,cascade = CascadeType.PERSIST)
    private Collection<MissionInfo> missionInfoCollection;

    public MissionId() {
    }

    public MissionId(Integer id) {
        this.id = id;
    }

    public MissionId(Integer id, String missionInfo) {
        this.id = id;
        this.missionInfo = missionInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMissionInfo() {
        return missionInfo;
    }

    public void setMissionInfo(String missionInfo) {
        this.missionInfo = missionInfo;
    }

    @XmlTransient
    public Collection<MissionInfo> getMissionInfoCollection() {
        return missionInfoCollection;
    }

    public void setMissionInfoCollection(Collection<MissionInfo> missionInfoCollection) {
        this.missionInfoCollection = missionInfoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MissionId)) {
            return false;
        }
        MissionId other = (MissionId) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mission.MissionId[ id=" + id + " ]";
    }
    
}
