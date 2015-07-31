package businessCharts.entityClasses;

import businessCharts.entityClasses.BranchName;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-30T12:49:48")
@StaticMetamodel(AssigneeData.class)
public class AssigneeData_ { 

    public static volatile SingularAttribute<AssigneeData, BranchName> branchid;
    public static volatile SingularAttribute<AssigneeData, String> assigneeName;
    public static volatile SingularAttribute<AssigneeData, Integer> clonedBugs;
    public static volatile SingularAttribute<AssigneeData, Integer> openBugs;
    public static volatile SingularAttribute<AssigneeData, Integer> id;
    public static volatile SingularAttribute<AssigneeData, BranchName> parentId;

}