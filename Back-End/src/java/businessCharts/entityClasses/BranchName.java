/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts.entityClasses;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ssingh2
 */
@Entity
@Table(name = "branch_name")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BranchName.findAll", query = "SELECT b FROM BranchName b"),
    @NamedQuery(name = "BranchName.findById", query = "SELECT b FROM BranchName b WHERE b.id = :id"),
    @NamedQuery(name = "BranchName.findByBranchName", query = "SELECT b FROM BranchName b WHERE b.branchName = :branchName"),
    @NamedQuery(name = "BranchName.findByStatus", query = "SELECT b FROM BranchName b WHERE b.status = :status")})
public class BranchName implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "branch_name")
    private String branchName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "branchName")
    private BranchParent branchParent;
    @OneToMany(mappedBy = "parentid")
    private Collection<BranchParent> branchParentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchName")
    private Collection<Allopenbugs> allopenbugsCollection;

    public BranchName() {
    }

    public BranchName(Integer id) {
        this.id = id;
    }

    public BranchName(Integer id, String branchName, String status) {
        this.id = id;
        this.branchName = branchName;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BranchParent getBranchParent() {
        return branchParent;
    }

    public void setBranchParent(BranchParent branchParent) {
        this.branchParent = branchParent;
    }

    @XmlTransient
    public Collection<BranchParent> getBranchParentCollection() {
        return branchParentCollection;
    }

    public void setBranchParentCollection(Collection<BranchParent> branchParentCollection) {
        this.branchParentCollection = branchParentCollection;
    }

    @XmlTransient
    public Collection<Allopenbugs> getAllopenbugsCollection() {
        return allopenbugsCollection;
    }

    public void setAllopenbugsCollection(Collection<Allopenbugs> allopenbugsCollection) {
        this.allopenbugsCollection = allopenbugsCollection;
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
        if (!(object instanceof BranchName)) {
            return false;
        }
        BranchName other = (BranchName) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.BranchName[ id=" + id + " ]";
    }
    
}
