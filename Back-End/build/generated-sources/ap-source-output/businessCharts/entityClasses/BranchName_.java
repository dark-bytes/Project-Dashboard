package businessCharts.entityClasses;

import businessCharts.entityClasses.Allopenbugs;
import businessCharts.entityClasses.BranchParent;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-16T12:19:18")
@StaticMetamodel(BranchName.class)
public class BranchName_ { 

    public static volatile CollectionAttribute<BranchName, Allopenbugs> allopenbugsCollection;
    public static volatile CollectionAttribute<BranchName, BranchParent> branchParentCollection;
    public static volatile SingularAttribute<BranchName, String> branchName;
    public static volatile SingularAttribute<BranchName, Integer> id;
    public static volatile SingularAttribute<BranchName, BranchParent> branchParent;
    public static volatile SingularAttribute<BranchName, String> status;

}