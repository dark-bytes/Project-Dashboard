/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.BranchName;
import businessCharts.entityClasses.BranchParent;
import businessCharts.entityClasses.FeatureBugTable;
import com.google.gson.annotations.Expose;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ssingh2
 */
public class GenerateMilestonesTLine {
    @Expose
    List<String> dates = new ArrayList<>();
    @Expose
    String branch;
    @Expose
    List<Integer> featurescount =  new ArrayList<>();
    @Expose
    List<Integer> defectscount =  new ArrayList<>();
    @Expose
    List< String > defects = new ArrayList<>();
    @Expose
    List< String > features = new ArrayList<>();
    @Expose
    ArrayList< String > defectsdates = new ArrayList<>();
    @Expose
    ArrayList< String > featuresdates = new ArrayList<>();
    @Expose
    ArrayList<String> colorArray = new ArrayList<>();
    
    private EntityManagerFactory factory;
    private String p_unit_name = "project_manage_dashboardPU";    
    
    public void generateJson(int _branchid) throws Exception{
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
        System.out.println("Tline Date" + _branchid);
        
        List<BranchParent> brp = em.createQuery("SELECT b from BranchParent b").getResultList();
        
        List<Integer> list = new ArrayList<Integer>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(_branchid);
        list.add(_branchid);
        
        //queue to just know the list of dependency / branch dependency in db
   /*     while(!queue.isEmpty()){
           int top = queue.element();
            queue.remove();
         //   System.out.println("top" + top);
            for(BranchParent bp : brp){
               // System.out.println(bp.getParentid().getId());
                if(bp.getParentid().getId() == top && bp.getId() != top){
                    System.out.println(bp.getId());
                    queue.add(bp.getId());
                    list.add(bp.getId());
                }
            }
        }*/
      //  System.out.println("Reached here");
        Query q = em.createQuery("SELECT f from FeatureBugTable f where f.branchId IN :id");
        q.setParameter("id", list);
        List<FeatureBugTable> _listFeatures = q.getResultList();
       // System.out.println("Reached here");
        q = em.createQuery("SELECT br from BranchName br where br.id = :id");
        q.setParameter("id", _branchid);
        List<BranchName> brname = q.getResultList();
        branch = brname.get(0).getBranchName();
       // System.out.println("Reached here");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(FeatureBugTable fBg : _listFeatures){
            System.out.println("Reached here");
            dates.add(sdf.format(fBg.getDate()));
            featuresdates.add(fBg.getFeaturesdate());
            defectsdates.add(fBg.getDefectsdate());
            features.add(fBg.getFeatures());
            defects.add(fBg.getDefects());
            defectscount.add(fBg.getDefectscount());
            featurescount.add(fBg.getFeaturecount());
            colorArray.add(fBg.getColorArray());
        }
        em.close();
        factory.close();
    }
}