/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.*;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ssingh2
 */
public class InsertBugDb {
    private static EntityManagerFactory factory;
    private static String p_unit_name = "project_manage_dashboardPU";
    
    public static void putData() {
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
        List<BranchParent> branches = em.createNamedQuery("BranchParent.findAll").getResultList();
        List<BranchName> branchparents = em.createNamedQuery("BranchName.findAll").getResultList();
       // for(BranchName br:branchparents){
         //   System.out.println(br.getId());
        //}
        Iterator<BranchParent> branch_iterate = branches.iterator();
        Iterator<BranchName> brparent = branchparents.iterator();
        while(brparent.hasNext()){
            BranchName br = brparent.next();
            String bchname = br.getBranchName();
            int id = br.getId();
            String colName = "Target Milestone";
            BranchName p_id = branch_iterate.next().getParentid();
            try {
                readExcell ex = new readExcell();
                Pair<Integer,Integer> countbugs = ex.countColKeywordClone(bchname,colName);
                System.out.println(bchname + " " + countbugs.getKey() + " " + countbugs.getValue());
                DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
                Allopenbugs allbugs = new Allopenbugs();
                
                allbugs.setOpenCloned(countbugs.getKey());
                allbugs.setOpenNotCloned(countbugs.getValue());
                allbugs.setParentId(p_id.getId());
                allbugs.setAllopenbugsPK(new AllopenbugsPK(new Date(),id));
                
                em.getTransaction().begin();
                em.persist(allbugs);
                em.getTransaction().commit();
                
            } catch (Exception ex) {
                Logger.getLogger(InsertBugDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        em.close();
        factory.close();
    }
}
