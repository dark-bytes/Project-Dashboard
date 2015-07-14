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
public class BugsListAssignee {
    private EntityManagerFactory factory;
    private String p_unit_name = "project_manage_dashboardPU";
    
    public void putAssigneeData() throws Exception{
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
        List<BranchName> list = em.createQuery("SELECT b FROM BranchName b").getResultList();
        Map< String,TreeMap< String,Pair< Integer, Integer> > > assigneeList = new TreeMap < > ();
       // AssigneeData ass = new AssigneeData();
        
        readExcell readexcell = new readExcell();
        assigneeList = readexcell.groupBycount("Target Milestone", "Assignee");
        
        for(BranchName bid : list ){
            TreeMap< String,Pair< Integer, Integer> > entry = assigneeList.get(bid.getBranchName());
            for(Map.Entry< String,Pair<Integer,Integer> > entry2 : entry.entrySet()){
                System.out.println(entry2.getValue().getValue() + " " + entry2.getValue().getKey() + " " + entry2.getKey()+ " " + bid.getId());
                AssigneeData ass = new AssigneeData();
                ass.setClonedBugs(entry2.getValue().getValue());
                ass.setOpenBugs(entry2.getValue().getKey());
                ass.setBranchid(bid.getId());
                ass.setAssigneeName(entry2.getKey());
                em.getTransaction().begin();
                em.persist(ass);
                em.getTransaction().commit();
            }
        }
        /*
        for(Map.Entry<String,TreeMap< String,Pair< Integer,Integer > > > entry: assigneeList.entrySet()){
            System.out.println(entry.getKey() + " ");
            for(Map.Entry< String,Pair<Integer,Integer> > entry2 : entry.getValue().entrySet()){
                System.out.print(entry2.getKey() + " ");
                System.out.println(entry2.getValue().getKey() + " " + entry2.getValue().getValue());
            }
        */
    }
}
