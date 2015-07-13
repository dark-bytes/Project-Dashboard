/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts.entityClasses;

import java.io.Serializable;
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
@Table(name = "assignee_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AssigneeData.findAll", query = "SELECT a FROM AssigneeData a"),
    @NamedQuery(name = "AssigneeData.findByAssigneeName", query = "SELECT a FROM AssigneeData a WHERE a.assigneeDataPK.assigneeName = :assigneeName"),
    @NamedQuery(name = "AssigneeData.findById", query = "SELECT a FROM AssigneeData a WHERE a.assigneeDataPK.id = :id"),
    @NamedQuery(name = "AssigneeData.findByOpenBugs", query = "SELECT a FROM AssigneeData a WHERE a.openBugs = :openBugs"),
    @NamedQuery(name = "AssigneeData.findByClonedBugs", query = "SELECT a FROM AssigneeData a WHERE a.clonedBugs = :clonedBugs")})
public class AssigneeData implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AssigneeDataPK assigneeDataPK;
    @Column(name = "openBugs")
    private Integer openBugs;
    @Column(name = "clonedBugs")
    private Integer clonedBugs;
    @JoinColumn(name = "id", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BranchName branchName;

    public AssigneeData() {
    }

    public AssigneeData(AssigneeDataPK assigneeDataPK) {
        this.assigneeDataPK = assigneeDataPK;
    }

    public AssigneeData(String assigneeName, int id) {
        this.assigneeDataPK = new AssigneeDataPK(assigneeName, id);
    }

    public AssigneeDataPK getAssigneeDataPK() {
        return assigneeDataPK;
    }

    public void setAssigneeDataPK(AssigneeDataPK assigneeDataPK) {
        this.assigneeDataPK = assigneeDataPK;
    }

    public Integer getOpenBugs() {
        return openBugs;
    }

    public void setOpenBugs(Integer openBugs) {
        this.openBugs = openBugs;
    }

    public Integer getClonedBugs() {
        return clonedBugs;
    }

    public void setClonedBugs(Integer clonedBugs) {
        this.clonedBugs = clonedBugs;
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
        hash += (assigneeDataPK != null ? assigneeDataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssigneeData)) {
            return false;
        }
        AssigneeData other = (AssigneeData) object;
        if ((this.assigneeDataPK == null && other.assigneeDataPK != null) || (this.assigneeDataPK != null && !this.assigneeDataPK.equals(other.assigneeDataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.AssigneeData[ assigneeDataPK=" + assigneeDataPK + " ]";
    }
    
}
