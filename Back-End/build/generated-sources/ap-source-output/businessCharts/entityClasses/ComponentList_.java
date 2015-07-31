package businessCharts.entityClasses;

import businessCharts.entityClasses.BranchName;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-30T12:49:48")
@StaticMetamodel(ComponentList.class)
public class ComponentList_ { 

    public static volatile SingularAttribute<ComponentList, BranchName> branchId;
    public static volatile SingularAttribute<ComponentList, Integer> cloned;
    public static volatile SingularAttribute<ComponentList, String> componentList;
    public static volatile SingularAttribute<ComponentList, Integer> id;
    public static volatile SingularAttribute<ComponentList, Integer> open;
    public static volatile SingularAttribute<ComponentList, BranchName> parentId;

}