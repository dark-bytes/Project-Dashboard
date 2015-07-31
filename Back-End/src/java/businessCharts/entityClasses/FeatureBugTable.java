/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts.entityClasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ssingh2
 */
@Entity
@Table(name = "feature_bug_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FeatureBugTable.findAll", query = "SELECT f FROM FeatureBugTable f"),
    @NamedQuery(name = "FeatureBugTable.findById", query = "SELECT f FROM FeatureBugTable f WHERE f.id = :id"),
    @NamedQuery(name = "FeatureBugTable.findByFeatures", query = "SELECT f FROM FeatureBugTable f WHERE f.features = :features"),
    @NamedQuery(name = "FeatureBugTable.findByDefects", query = "SELECT f FROM FeatureBugTable f WHERE f.defects = :defects"),
    @NamedQuery(name = "FeatureBugTable.findByDate", query = "SELECT f FROM FeatureBugTable f WHERE f.date = :date"),
    @NamedQuery(name = "FeatureBugTable.findByFeaturecount", query = "SELECT f FROM FeatureBugTable f WHERE f.featurecount = :featurecount"),
    @NamedQuery(name = "FeatureBugTable.findByDefectscount", query = "SELECT f FROM FeatureBugTable f WHERE f.defectscount = :defectscount"),
    @NamedQuery(name = "FeatureBugTable.findByColorArray", query = "SELECT f FROM FeatureBugTable f WHERE f.colorArray = :colorArray")})
public class FeatureBugTable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 300)
    @Column(name = "features")
    private String features;
    @Size(max = 300)
    @Column(name = "defects")
    private String defects;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "featuresdate")
    private String featuresdate;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "defectsdate")
    private String defectsdate;
    @Column(name = "featurecount")
    private Integer featurecount;
    @Column(name = "defectscount")
    private Integer defectscount;
    @Size(max = 45)
    @Column(name = "colorArray")
    private String colorArray;
    @JoinColumn(name = "branch_id", referencedColumnName = "ID")
    @ManyToOne
    private BranchName branchId;
    @JoinColumn(name = "parent_id", referencedColumnName = "ID")
    @ManyToOne
    private BranchName parentId;

    public FeatureBugTable() {
    }

    public FeatureBugTable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDefects() {
        return defects;
    }

    public void setDefects(String defects) {
        this.defects = defects;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFeaturesdate() {
        return featuresdate;
    }

    public void setFeaturesdate(String featuresdate) {
        this.featuresdate = featuresdate;
    }

    public String getDefectsdate() {
        return defectsdate;
    }

    public void setDefectsdate(String defectsdate) {
        this.defectsdate = defectsdate;
    }

    public Integer getFeaturecount() {
        return featurecount;
    }

    public void setFeaturecount(Integer featurecount) {
        this.featurecount = featurecount;
    }

    public Integer getDefectscount() {
        return defectscount;
    }

    public void setDefectscount(Integer defectscount) {
        this.defectscount = defectscount;
    }

    public String getColorArray() {
        return colorArray;
    }

    public void setColorArray(String colorArray) {
        this.colorArray = colorArray;
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
        if (!(object instanceof FeatureBugTable)) {
            return false;
        }
        FeatureBugTable other = (FeatureBugTable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "businessCharts.entityClasses.FeatureBugTable[ id=" + id + " ]";
    }
    
}
