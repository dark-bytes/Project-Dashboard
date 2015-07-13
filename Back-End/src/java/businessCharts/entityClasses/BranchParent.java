/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts.entityClasses;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ssingh2
 */
@Entity
@Table(name = "branch_parent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BranchParent.findAll", query = "SELECT b FROM BranchParent b"),
    @NamedQuery(name = "BranchParent.findById", query = "SELECT b FROM BranchParent b WHERE b.id = :id")})
public class BranchParent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BranchName branchName;
    @JoinColumn(name = "parentid", referencedColumnName = "ID")
    @ManyToOne
    private BranchName parentid;

    public BranchParent() {
    }

    public BranchParent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BranchName getBranchName() {
        return branchName;
    }

    public void setBranchName(BranchName branchName) {
        this.branchName = branchName;
    }

    public BranchName getParentid() {
        return parentid;
    }

    public void setParentid(BranchName parentid) {
        this.parentid = parentid;
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
        if (!(object instanceof BranchParent)) {
            return false;
        }
        BranchParent other = (BranchParent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.BranchParent[ id=" + id + " ]";
    }
    
}
