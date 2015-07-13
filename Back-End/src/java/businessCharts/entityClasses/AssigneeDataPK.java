/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts.entityClasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ssingh2
 */
@Embeddable
public class AssigneeDataPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "assignee_name")
    private String assigneeName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;

    public AssigneeDataPK() {
    }

    public AssigneeDataPK(String assigneeName, int id) {
        this.assigneeName = assigneeName;
        this.id = id;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assigneeName != null ? assigneeName.hashCode() : 0);
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssigneeDataPK)) {
            return false;
        }
        AssigneeDataPK other = (AssigneeDataPK) object;
        if ((this.assigneeName == null && other.assigneeName != null) || (this.assigneeName != null && !this.assigneeName.equals(other.assigneeName))) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.AssigneeDataPK[ assigneeName=" + assigneeName + ", id=" + id + " ]";
    }
    
}
