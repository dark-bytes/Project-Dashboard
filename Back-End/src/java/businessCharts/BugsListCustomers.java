/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.BranchName;
import businessCharts.entityClasses.BranchParent;
import businessCharts.entityClasses.ComponentList;
import businessCharts.entityClasses.CustomerList;
import java.util.List;
import java.util.Map;
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
public class BugsListCustomers extends AbstractBugList{
    
    @Override
    public void put() throws Exception{
        initializeFactory();
        Map< String,TreeMap< String,Pair< Integer, Integer> > > customerList = new TreeMap < > ();
        List<BranchName> list = em.createQuery("SELECT b FROM BranchName b").getResultList();
 //       int index = 0;
        readExcell readexcell = new readExcell();
        customerList = readexcell.groupBycount("Target Milestone", "Customer");
        
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE customer_list").executeUpdate();
        em.getTransaction().commit();
        for(BranchName bid : list ){
            TreeMap< String,Pair< Integer, Integer> > entry = customerList.get(bid.getBranchName());
//            int parentId = list.get(index++).getBranchParent().getParentid().getId();
            
            try{
                for(Map.Entry< String,Pair<Integer,Integer> > entry2 : entry.entrySet()){
                    System.out.println(entry2.getValue().getValue() + " " + entry2.getValue().getKey() + " " + entry2.getKey()+ " " + bid.getId());

                    CustomerList cL = new CustomerList();
                    cL.setClonedbugs(entry2.getValue().getKey());
                    cL.setOpenbugs(entry2.getValue().getValue());
                    cL.setBranchId(bid);
                    cL.setCustomerName(entry2.getKey());
                    Query q = em.createQuery("SELECT b FROM BranchParent b WHERE b.id = :id");
                    q.setParameter("id", bid.getId());
                    List<BranchParent> brp = q.getResultList();
                    cL.setParentId(brp.get(0).getParentid());
                    em.getTransaction().begin();
                    em.persist(cL);
                    em.getTransaction().commit();
                }
            }
            catch(Exception ex){}
        }
        closeFactory();
    }
}
