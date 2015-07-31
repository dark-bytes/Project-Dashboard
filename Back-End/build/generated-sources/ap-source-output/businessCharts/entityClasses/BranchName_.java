package businessCharts.entityClasses;

import businessCharts.entityClasses.Allopenbugs;
import businessCharts.entityClasses.AssigneeData;
import businessCharts.entityClasses.BranchParent;
import businessCharts.entityClasses.ComponentList;
import businessCharts.entityClasses.CustomerList;
import businessCharts.entityClasses.FeatureBugTable;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-30T12:49:48")
@StaticMetamodel(BranchName.class)
public class BranchName_ { 

    public static volatile CollectionAttribute<BranchName, Allopenbugs> allopenbugsCollection1;
    public static volatile CollectionAttribute<BranchName, Allopenbugs> allopenbugsCollection;
    public static volatile CollectionAttribute<BranchName, BranchParent> branchParentCollection;
    public static volatile CollectionAttribute<BranchName, ComponentList> componentListCollection1;
    public static volatile CollectionAttribute<BranchName, ComponentList> componentListCollection;
    public static volatile CollectionAttribute<BranchName, FeatureBugTable> featureBugTableCollection1;
    public static volatile SingularAttribute<BranchName, String> branchName;
    public static volatile CollectionAttribute<BranchName, FeatureBugTable> featureBugTableCollection;
    public static volatile SingularAttribute<BranchName, BranchParent> branchParent;
    public static volatile CollectionAttribute<BranchName, CustomerList> customerListCollection;
    public static volatile CollectionAttribute<BranchName, AssigneeData> assigneeDataCollection;
    public static volatile CollectionAttribute<BranchName, AssigneeData> assigneeDataCollection1;
    public static volatile CollectionAttribute<BranchName, CustomerList> customerListCollection1;
    public static volatile SingularAttribute<BranchName, Integer> id;
    public static volatile SingularAttribute<BranchName, String> status;

}