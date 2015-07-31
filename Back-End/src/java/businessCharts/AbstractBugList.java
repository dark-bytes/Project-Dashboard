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
public abstract class AbstractBugList{
    String p_unit_name;
    EntityManagerFactory factory;
    EntityManager em;
    
    public void initializeFactory(){
        p_unit_name =  "project_manage_dashboardPU";
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        em = factory.createEntityManager();
    }
    public void closeFactory(){
        em.close();
        factory.close();
    }
    public abstract void put() throws Exception;
}
