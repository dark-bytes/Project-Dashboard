/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;
import businessCharts.entityClasses.BranchName;
import businessCharts.entityClasses.BranchParent;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Query;

/**
 *
 * @author ssingh2
 */
public class DependencyInsert extends AbstractBugList{
    @Expose
    List<String> status = new ArrayList<>();
    @Expose
    List<Integer> id = new ArrayList<>();
    @Expose
    List<DependencyGenerator> add = new ArrayList<>();
    @Expose 
    List<Integer> delete = new ArrayList<>();
        //I have to put he dates hear just remember it.
    @Override
    public void put() throws Exception {
        initializeFactory();
        List<BranchName> brnameList = em.createNamedQuery("BranchName.findAll").getResultList();
        Map<String,Integer> brmap = new TreeMap<String,Integer>();
        Map<Integer,Integer> idmap = new TreeMap<>();                       //helping in add + delete operation
     //   System.out.println("Deleted id->" + id);
        for(BranchName brn : brnameList)
            brmap.put(brn.getBranchName(),brn.getId());
     //   System.out.println("Deleted id->" + id);
        closeFactory();
        //addition of dependency 
        for(DependencyGenerator Dpgenerate : add){
            
            initializeFactory();
            BranchName brn = new BranchName();
            brn.setBranchName(Dpgenerate.getBranchName());
            brn.setStatus(Dpgenerate.getStatus());
            em.getTransaction().begin();
            em.persist(brn);
            em.getTransaction().commit();
            Integer id2 = brn.getId();
            closeFactory();
            
            initializeFactory();
            BranchParent brp = new BranchParent();                     //element to be added
            brp.setId(id2);
            System.out.println(Dpgenerate.getParentName());
            
            Query q = em.createQuery("SELECT b FROM BranchName b WHERE b.branchName = :branchName"); //query for setting parent id
            q.setParameter("branchName", Dpgenerate.getParentName());
            
            List<BranchName> brnfront = q.getResultList();
            
            System.out.println("Size of branchparent in getdependency" + brnfront.size());
            brp.setParentid(brnfront.get(0));
            System.out.println("Parent id is" + brnfront.get(0).getId());
            
            em.getTransaction().begin();
            em.persist(brp);
            em.getTransaction().commit();
            //mappping to id map if it is deleted then can take help of it
            idmap.put(Dpgenerate.getBranchId(), brp.getId());
            closeFactory();
        }
        //updating the names
        initializeFactory();
        int index = 0;
        for(String s:status){
            Query q = em.createQuery("UPDATE BranchName b SET b.status = :status WHERE b.id = :id");
            q.setParameter("status", s);
            q.setParameter("id", id.get(index));
            em.getTransaction().begin();
            q.executeUpdate();
            em.getTransaction().commit();
            index++;
        }
        //deleting the branch and dependencies
        System.out.println("Deleted id->" + id);
        index = 0;
        for(Integer i:delete){
            System.out.println("Deleted id->" + id);
            if(i < 0){
                int id1 = idmap.get(i);
                perform_delete(id1);
            }
            else
                perform_delete(i);
        }
   //     MilestonesTableEdit mstonedit = new MilestonesTableEdit();
   //     mstonedit.onUpload();
        BugsListAssignee bugsListAssignee = new BugsListAssignee();
        bugsListAssignee.put();
        BugsListCustomers bugsListCustomers = new BugsListCustomers();
        bugsListCustomers.put();
        BugsListComponent bugsListComponent = new BugsListComponent();
        bugsListComponent.put();
        em.close();
        factory.close();
    }
    private void perform_delete(int id) throws Exception{
        System.out.println("Deleted id->" + id);
        Query q  = em.createQuery("SELECT b FROM BranchParent b WHERE b.parentid.id = :id");
        q.setParameter("id", id);
        List<BranchParent> brpList = q.getResultList();
        System.out.println("Deleted id->" + id);
        for(BranchParent brp : brpList){
            System.out.println("Id to be deleted" + brp.getId());
            if(id != brp.getId())
                perform_delete(brp.getId());
        }
        System.out.println("Deleted id->" + id);
        q = em.createQuery("DELETE FROM BranchName b WHERE b.id = :id");
        q.setParameter("id", id);
        try{
            em.getTransaction().begin();
            q.executeUpdate();
            em.getTransaction().commit();
        }
        catch(Exception ex){
            
        }
    }
}