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
import javax.persistence.Query;

/**
 *
 * @author ssingh2
 */
public class InsertBugDb extends AbstractBugList{
    public void putData(Date date) throws Exception{
        initializeFactory();
        List<BranchName> branchparents = em.createNamedQuery("BranchName.findAll").getResultList();
        Iterator<BranchName> brparent = branchparents.iterator();
        while(brparent.hasNext()){
            BranchName br = brparent.next();
            String bchname = br.getBranchName();
            int id = br.getId();
            String colName = "Target Milestone";
            Query q = em.createQuery("SELECT b FROM BranchParent b WHERE b.id = :id");
            q.setParameter("id", br.getId());
            List<BranchParent> brp = q.getResultList();
            BranchName p_id = brp.get(0).getParentid();
            try {
                Query toOverwrite = em.createQuery("SELECT b from Allopenbugs b WHERE b.branchName = :brname AND b.allopenbugsPK.date = :date");
                toOverwrite.setParameter("brname", br);
                toOverwrite.setParameter("date", date);
                //this is done in order to overwrite if same date and branch present.
                List<Allopenbugs> getlist = toOverwrite.getResultList();
                if(getlist.size() > 0){
                    toOverwrite = em.createQuery("DELETE FROM Allopenbugs b WHERE b.branchName = :brname AND b.allopenbugsPK.date = :date");
                    toOverwrite.setParameter("brname", br);
                    toOverwrite.setParameter("date", date);
                    em.getTransaction().begin();
                    toOverwrite.executeUpdate();
                    em.getTransaction().commit();
                }
                
                readExcell ex = new readExcell();
                Pair<Integer,Integer> countbugs = ex.countColKeywordClone(bchname,colName);
                System.out.println(bchname + " " + countbugs.getKey() + " " + countbugs.getValue());
                Allopenbugs allbugs = new Allopenbugs();
                
                allbugs.setOpenCloned(countbugs.getKey());
                allbugs.setOpenNotCloned(countbugs.getValue());
                allbugs.setParentId(p_id);
                allbugs.setAllopenbugsPK(new AllopenbugsPK(date,id));
                
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

    @Override
    public void put() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
