package businessCharts.entityClasses;

import businessCharts.entityClasses.BranchName;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-30T12:49:48")
@StaticMetamodel(FeatureBugTable.class)
public class FeatureBugTable_ { 

    public static volatile SingularAttribute<FeatureBugTable, Date> date;
    public static volatile SingularAttribute<FeatureBugTable, Integer> featurecount;
    public static volatile SingularAttribute<FeatureBugTable, Integer> defectscount;
    public static volatile SingularAttribute<FeatureBugTable, String> features;
    public static volatile SingularAttribute<FeatureBugTable, String> colorArray;
    public static volatile SingularAttribute<FeatureBugTable, BranchName> branchId;
    public static volatile SingularAttribute<FeatureBugTable, String> defects;
    public static volatile SingularAttribute<FeatureBugTable, String> defectsdate;
    public static volatile SingularAttribute<FeatureBugTable, Integer> id;
    public static volatile SingularAttribute<FeatureBugTable, String> featuresdate;
    public static volatile SingularAttribute<FeatureBugTable, BranchName> parentId;

}