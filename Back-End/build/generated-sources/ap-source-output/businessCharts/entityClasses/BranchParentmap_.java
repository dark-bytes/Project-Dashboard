package businessCharts.entityClasses;

import businessCharts.entityClasses.BranchNames;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-13T11:11:19")
@StaticMetamodel(BranchParentmap.class)
public class BranchParentmap_ { 

    public static volatile SingularAttribute<BranchParentmap, String> branchName;
    public static volatile SingularAttribute<BranchParentmap, Integer> id;
    public static volatile CollectionAttribute<BranchParentmap, BranchNames> branchNamesCollection;
    public static volatile SingularAttribute<BranchParentmap, BranchNames> branchNames;

}