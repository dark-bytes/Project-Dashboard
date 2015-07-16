/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.AssigneeData;
import businessCharts.entityClasses.BranchName;
import businessCharts.entityClasses.ComponentList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ssingh2
 */
public class BugsListComponent extends AbstractBugList{
    
    @Override
    public void put() throws Exception{
        Map< String,TreeMap< String,Pair< Integer, Integer> > > componentList = new TreeMap < > ();
        List<BranchName> list = em.createNamedQuery("BranchName.findAll").getResultList();
        int index = 0;
        readExcell readexcell = new readExcell();
        componentList = readexcell.groupBycount("Target Milestone", "Component");
        
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE component_list").executeUpdate();
        em.getTransaction().commit();
        
        for(BranchName bid : list ){
            TreeMap< String,Pair< Integer, Integer> > entry = componentList.get(bid.getBranchName());
            int parentId = list.get(index++).getBranchParent().getParentid().getId();
            
            for(Map.Entry< String,Pair<Integer,Integer> > entry2 : entry.entrySet()){
                System.out.println(entry2.getValue().getValue() + " " + entry2.getValue().getKey() + " " + entry2.getKey()+ " " + bid.getId());
                
                ComponentList cL = new ComponentList();
                cL.setCloned(entry2.getValue().getValue());
                cL.setOpen(entry2.getValue().getKey());
                cL.setBranchId(bid.getId());
                cL.setComponentList(entry2.getKey());
                cL.setParentId(parentId);
                
                em.getTransaction().begin();
                em.persist(cL);
                em.getTransaction().commit();
            }
        }
    }
}
