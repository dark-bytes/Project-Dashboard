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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ssingh2
 */
@Entity
@Table(name = "customer_list")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerList.findAll", query = "SELECT c FROM CustomerList c"),
    @NamedQuery(name = "CustomerList.findById", query = "SELECT c FROM CustomerList c WHERE c.id = :id"),
    @NamedQuery(name = "CustomerList.findByCustomerName", query = "SELECT c FROM CustomerList c WHERE c.customerName = :customerName"),
    @NamedQuery(name = "CustomerList.findByOpenbugs", query = "SELECT c FROM CustomerList c WHERE c.openbugs = :openbugs"),
    @NamedQuery(name = "CustomerList.findByClonedbugs", query = "SELECT c FROM CustomerList c WHERE c.clonedbugs = :clonedbugs")})
public class CustomerList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 200)
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "openbugs")
    private Integer openbugs;
    @Column(name = "clonedbugs")
    private Integer clonedbugs;
    @JoinColumn(name = "branch_id", referencedColumnName = "ID")
    @ManyToOne
    private BranchName branchId;
    @JoinColumn(name = "parent_id", referencedColumnName = "ID")
    @ManyToOne
    private BranchName parentId;

    public CustomerList() {
    }

    public CustomerList(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getOpenbugs() {
        return openbugs;
    }

    public void setOpenbugs(Integer openbugs) {
        this.openbugs = openbugs;
    }

    public Integer getClonedbugs() {
        return clonedbugs;
    }

    public void setClonedbugs(Integer clonedbugs) {
        this.clonedbugs = clonedbugs;
    }

    public BranchName getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchName branchId) {
        this.branchId = branchId;
    }

    public BranchName getParentId() {
        return parentId;
    }

    public void setParentId(BranchName parentId) {
        this.parentId = parentId;
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
        if (!(object instanceof CustomerList)) {
            return false;
        }
        CustomerList other = (CustomerList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.CustomerList[ id=" + id + " ]";
    }
    
}
