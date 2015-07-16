package businessCharts.entityClasses;

import businessCharts.entityClasses.BranchName;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-16T12:19:18")
@StaticMetamodel(BranchParent.class)
public class BranchParent_ { 

    public static volatile SingularAttribute<BranchParent, BranchName> branchName;
    public static volatile SingularAttribute<BranchParent, Integer> id;
    public static volatile SingularAttribute<BranchParent, BranchName> parentid;

}