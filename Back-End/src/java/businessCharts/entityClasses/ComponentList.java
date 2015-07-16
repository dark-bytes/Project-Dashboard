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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ssingh2
 */
@Entity
@Table(name = "component_list")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponentList.findAll", query = "SELECT c FROM ComponentList c"),
    @NamedQuery(name = "ComponentList.findById", query = "SELECT c FROM ComponentList c WHERE c.id = :id"),
    @NamedQuery(name = "ComponentList.findByComponentList", query = "SELECT c FROM ComponentList c WHERE c.componentList = :componentList"),
    @NamedQuery(name = "ComponentList.findByBranchId", query = "SELECT c FROM ComponentList c WHERE c.branchId = :branchId"),
    @NamedQuery(name = "ComponentList.findByParentId", query = "SELECT c FROM ComponentList c WHERE c.parentId = :parentId"),
    @NamedQuery(name = "ComponentList.findByOpen", query = "SELECT c FROM ComponentList c WHERE c.open = :open"),
    @NamedQuery(name = "ComponentList.findByCloned", query = "SELECT c FROM ComponentList c WHERE c.cloned = :cloned")})
public class ComponentList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 200)
    @Column(name = "component_list")
    private String componentList;
    @Column(name = "branch_id")
    private Integer branchId;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "open")
    private Integer open;
    @Column(name = "cloned")
    private Integer cloned;

    public ComponentList() {
    }

    public ComponentList(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComponentList() {
        return componentList;
    }

    public void setComponentList(String componentList) {
        this.componentList = componentList;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getCloned() {
        return cloned;
    }

    public void setCloned(Integer cloned) {
        this.cloned = cloned;
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
        if (!(object instanceof ComponentList)) {
            return false;
        }
        ComponentList other = (ComponentList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.ComponentList[ id=" + id + " ]";
    }
    
}
