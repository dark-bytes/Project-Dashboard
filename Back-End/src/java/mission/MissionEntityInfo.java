/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SUDHANSHU
 */
@Entity
@Table(name = "mission_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MissionInfo.findAll", query = "SELECT m FROM MissionInfo m"),
    @NamedQuery(name = "MissionInfo.findById", query = "SELECT m FROM MissionInfo m WHERE m.id = :id"),
    @NamedQuery(name = "MissionInfo.findByInfo", query = "SELECT m FROM MissionInfo m WHERE m.info = :info")})
public class MissionEntityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Expose
    private Integer id;
    @Basic(optional = false)
    @Expose
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "info")
    private String info;
    @JoinColumn(name = "mission_id", referencedColumnName = "id")
    @ManyToOne
    private MissionEntityId missionId;

    public MissionEntityInfo() {
    }

    public MissionEntityInfo(Integer id) {
        this.id = id;
    }

    public MissionEntityInfo(Integer id, String info) {
        this.id = id;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public MissionEntityId getMissionId() {
        return missionId;
    }

    public void setMissionId(MissionEntityId missionId) {
        this.missionId = missionId;
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
        if (!(object instanceof MissionEntityInfo)) {
            return false;
        }
        MissionEntityInfo other = (MissionEntityInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mission.MissionInfo[ id=" + id + " ]";
    }
    
}
