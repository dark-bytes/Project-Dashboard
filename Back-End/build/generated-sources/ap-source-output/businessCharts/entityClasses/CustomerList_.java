package businessCharts.entityClasses;

import businessCharts.entityClasses.BranchName;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-30T12:49:48")
@StaticMetamodel(CustomerList.class)
public class CustomerList_ { 

    public static volatile SingularAttribute<CustomerList, BranchName> branchId;
    public static volatile SingularAttribute<CustomerList, Integer> clonedbugs;
    public static volatile SingularAttribute<CustomerList, Integer> id;
    public static volatile SingularAttribute<CustomerList, Integer> openbugs;
    public static volatile SingularAttribute<CustomerList, String> customerName;
    public static volatile SingularAttribute<CustomerList, BranchName> parentId;

}