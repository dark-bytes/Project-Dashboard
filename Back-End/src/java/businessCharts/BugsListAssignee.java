/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.AssigneeData;
import businessCharts.entityClasses.BranchName;
import businessCharts.entityClasses.BranchParent;
import java.util.*;
import java.util.TreeMap;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ssingh2
 */
public class BugsListAssignee extends AbstractBugList{
    
    @Override
    public void put() throws Exception{
        initializeFactory();
        List<BranchName> list = em.createQuery("SELECT b FROM BranchName b").getResultList();
        Map< String,TreeMap< String,Pair< Integer, Integer> > > assigneeList = new TreeMap < > ();
       // AssigneeData ass = new AssigneeData();
        int index = 0;
        readExcell readexcell = new readExcell();
        assigneeList = readexcell.groupBycount("Target Milestone", "Assignee");
        
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE assignee_data").executeUpdate();
        em.getTransaction().commit();
        System.out.println(assigneeList.size());
        for(BranchName bid : list ){
            TreeMap< String,Pair< Integer, Integer> > entry = assigneeList.get(bid.getBranchName());
         //   int parentId = list.get(index++).getBranchParent().getParentid().getId();
            System.out.println("Inside BranchName tellme" + bid.getBranchName());
            try{
                for(Map.Entry< String,Pair<Integer,Integer> > entry2 : entry.entrySet()){
                    System.out.println(entry2.getValue().getValue() + " " + entry2.getValue().getKey() + " " + entry2.getKey()+ " " + bid.getId());

                    AssigneeData ass = new AssigneeData();
                    ass.setClonedBugs(entry2.getValue().getKey());
                    ass.setOpenBugs(entry2.getValue().getValue());
                    ass.setBranchid(bid);
                    ass.setAssigneeName(entry2.getKey());
                    Query q = em.createQuery("SELECT b FROM BranchParent b WHERE b.id = :id");
                    q.setParameter("id", bid.getId());
                    List<BranchParent> brp = q.getResultList();
                    ass.setParentId(brp.get(0).getParentid());
                    em.getTransaction().begin();
                    em.persist(ass);
                    em.getTransaction().commit();
                }
            }
            catch(Exception ex){}
        }
        closeFactory();
    }
}
