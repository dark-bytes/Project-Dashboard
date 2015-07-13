/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts.entityClasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ssingh2
 */
@Entity
@Table(name = "allopenbugs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Allopenbugs.findAll", query = "SELECT a FROM Allopenbugs a"),
    @NamedQuery(name = "Allopenbugs.findByDate", query = "SELECT a FROM Allopenbugs a WHERE a.allopenbugsPK.date = :date"),
    @NamedQuery(name = "Allopenbugs.findByOpenCloned", query = "SELECT a FROM Allopenbugs a WHERE a.openCloned = :openCloned"),
    @NamedQuery(name = "Allopenbugs.findByOpenNotCloned", query = "SELECT a FROM Allopenbugs a WHERE a.openNotCloned = :openNotCloned"),
    @NamedQuery(name = "Allopenbugs.findByBranchId", query = "SELECT a FROM Allopenbugs a WHERE a.allopenbugsPK.branchId = :branchId"),
    @NamedQuery(name = "Allopenbugs.findByParentId", query = "SELECT a FROM Allopenbugs a WHERE a.parentId = :parentId")})
public class Allopenbugs implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AllopenbugsPK allopenbugsPK;
    @Column(name = "openCloned")
    private Integer openCloned;
    @Column(name = "openNotCloned")
    private Integer openNotCloned;
    @Column(name = "parent_id")
    private Integer parentId;
    @JoinColumn(name = "branch_id", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BranchName branchName;

    public Allopenbugs() {
    }

    public Allopenbugs(AllopenbugsPK allopenbugsPK) {
        this.allopenbugsPK = allopenbugsPK;
    }

    public Allopenbugs(Date date, int branchId) {
        this.allopenbugsPK = new AllopenbugsPK(date, branchId);
    }

    public AllopenbugsPK getAllopenbugsPK() {
        return allopenbugsPK;
    }

    public void setAllopenbugsPK(AllopenbugsPK allopenbugsPK) {
        this.allopenbugsPK = allopenbugsPK;
    }

    public Integer getOpenCloned() {
        return openCloned;
    }

    public void setOpenCloned(Integer openCloned) {
        this.openCloned = openCloned;
    }

    public Integer getOpenNotCloned() {
        return openNotCloned;
    }

    public void setOpenNotCloned(Integer openNotCloned) {
        this.openNotCloned = openNotCloned;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public BranchName getBranchName() {
        return branchName;
    }

    public void setBranchName(BranchName branchName) {
        this.branchName = branchName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (allopenbugsPK != null ? allopenbugsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Allopenbugs)) {
            return false;
        }
        Allopenbugs other = (Allopenbugs) object;
        if ((this.allopenbugsPK == null && other.allopenbugsPK != null) || (this.allopenbugsPK != null && !this.allopenbugsPK.equals(other.allopenbugsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.Allopenbugs[ allopenbugsPK=" + allopenbugsPK + " ]";
    }
    
}
