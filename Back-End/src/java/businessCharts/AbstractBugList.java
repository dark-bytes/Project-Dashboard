/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ssingh2
 */
public abstract class AbstractBugList {
    EntityManagerFactory factory;
    String p_unit_name = "project_manage_dashboardPU";    
    EntityManager em;
    public abstract void put() throws Exception;
    public void initializeFactory(){
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
    }
}
