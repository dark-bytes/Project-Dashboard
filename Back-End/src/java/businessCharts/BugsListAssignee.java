/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.BranchParent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ssingh2
 */
public class BugsListAssignee {
    private EntityManagerFactory factory;
    private String p_unit_name = "project_manage_dashboardPU";
    
    public void putAssigneeData() throws Exception{
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
        List<BranchParent> list = em.createQuery("SELECT b FROM BranchParent b").getResultList();
        readExcell readexcell = new readExcell();
        readexcell.groupBycount("Target Milestone", "Assignee");
    }
}
