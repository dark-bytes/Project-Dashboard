/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.BranchName;
import businessCharts.entityClasses.FeatureBugTable;
import com.google.gson.annotations.Expose;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.util.Pair;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author ssingh2
 */
public class MilestonesTableEdit extends AbstractBugList{
    @Expose
    List<String> branchList = new ArrayList<>();                    //this for autocomplete used in db
    @Expose
    List<String> dates = new ArrayList<>();                         //list of release dates
    @Expose
    List<String> branch = new ArrayList<>();                       //it contain detail to which branch it is added
    @Expose
    List<String> features = new ArrayList<>();                     //list of features
    @Expose
    List<String> defects = new ArrayList<>();                     //list of defects
    @Expose
    List<Integer> milestoneId = new ArrayList<>();                //list of milestonesid no use if you see in putdatadb function
    
    public void onUpload() throws Exception{
        initializeFactory();
        readExcell readexcell = new readExcell();
        TreeMap<Double,String> findList = readexcell.getcontentList("Bug Id","Deadline");
        List<FeatureBugTable> Flist = em.createNamedQuery("FeatureBugTable.findAll").getResultList();
        System.out.println("reached");
        
        em.getTransaction().begin();
        em.createNativeQuery("truncate table feature_bug_table").executeUpdate();
        em.getTransaction().commit();
        
        for(int j = 0;j < Flist.size();j++){
            FeatureBugTable bug = new FeatureBugTable();
            FeatureBugTable bugAt = Flist.get(j);
            int updated_defects = 0;
            int updated_feature= 0;
            bug.setColorArray("green");
            
            String features[] = bugAt.getFeatures().split(",");
           // String featuresDates[] = bugs.getFeaturesdate().split(",");
            String updatedFeature = "";
            String updatedFtrDates = "";
            //System.out.println("Updateddefects" + features.length + features[0])
            System.out.println("reached");
            for(int i = 0;i < features.length && !"".equals(features[i]);i++){
                
                if(findList.containsKey(Double.parseDouble(features[i]))){
                    //updatedFeature = updatedFeature + features[i];
                    
                    updated_feature++;
                    if(!"nodate".equals(findList.get(Double.parseDouble(features[i])))){
                        updatedFtrDates = updatedFtrDates + findList.get(Double.parseDouble(features[i])) + ",";
                        if(new SimpleDateFormat("yyyy-MM-dd").parse(findList.get(Double.parseDouble(features[i]))).after((bugAt.getDate())))
                            bug.setColorArray("red");
                    }
                    else
                        updatedFtrDates = updatedFtrDates + "Date Not Available" + ",";
                }
                else
                    updatedFtrDates = updatedFtrDates + "Bug not available/Resolved" + ",";
            }
            updatedFtrDates = updatedFtrDates.replaceAll(",$", "");
            System.out.println("reached");
            
            String defects[] = bugAt.getDefects().split(",");
            
            String updatedDefects = "";
            String updatedDefectsDates = "";
         //   System.out.println("Updateddefects" + s.length + s[0]);
            System.out.println("reached");
            
            for(int i = 0;i < defects.length && !"".equals(defects[i]);i++){
                if(findList.containsKey(Double.parseDouble(defects[i]))){
                   // updatedDefects = updatedDefects + defects[i];                    
                    updated_defects++;
                    if(!"nodate".equals(findList.get(Double.parseDouble(defects[i])))){
                        updatedDefectsDates = updatedDefectsDates + findList.get(Double.parseDouble(defects[i])) + ",";
                        if(new SimpleDateFormat("yyyy-MM-dd").parse(findList.get(Double.parseDouble(defects[i]))).after((bugAt.getDate())))
                            bug.setColorArray("red");
                    }
                    else
                        updatedDefectsDates = updatedDefectsDates + "Date Not Available" + ",";
                }
                else
                    updatedDefectsDates = updatedDefectsDates + "Bug not available/Resolved" + ",";
            }            
            updatedDefectsDates = updatedDefectsDates.replaceAll(",$","");
            System.out.println("reached");
            System.out.println("Updateddefects" + updatedDefects.length() + updatedFeature.length());
            if(updated_defects == 0 && updated_feature == 0)
                bug.setColorArray("black");
                
                bug.setDate(bugAt.getDate());
                bug.setBranchId(bugAt.getBranchId());
                bug.setParentId(bugAt.getParentId());
                bug.setDefectscount(updated_defects);                
                bug.setFeaturecount(updated_feature);
                bug.setFeatures(bugAt.getFeatures());
                bug.setDefects(bugAt.getDefects());
                bug.setFeaturesdate(updatedFtrDates);
                bug.setDefectsdate(updatedDefectsDates);
                
                em.getTransaction().begin();
                em.persist(bug);
                em.getTransaction().commit();
                
                
            System.out.println("reached");
        }
        closeFactory();
    }
    
    
    public void putDataDb() throws Exception{
        initializeFactory();
        readExcell readexcell = new readExcell();
        Query q = em.createQuery("SELECT b from BranchName b where b.branchName = :name");
       // System.out.println("Size of date array->" + dates.size());
        for(int i = 0;i < dates.size();i++){
            
                Query q_to_update = em.createQuery("SELECT fbt from FeatureBugTable fbt where fbt.branchId = :id AND fbt.date = :date");
                
                FeatureBugTable fBg = new FeatureBugTable();
                fBg.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(i)));                           //added

                q.setParameter("name", branch.get(i));
                List<BranchName> brname = q.getResultList();           
                q_to_update.setParameter("id", brname.get(0));
                q_to_update.setParameter("date",new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(i)));
                List<FeatureBugTable> temp_fBt = q_to_update.getResultList();
                
                
                //this condition id for updating if previous row with
                //same branchname and date exists in db
                // if exists
                // then add content of it of features and defects and delete
//                //else nothing
                if(temp_fBt.size() > 0){
                    for(int j = 0;j < temp_fBt.size();j++)
                    {
                        features.set(i,features.get(i).concat("," + temp_fBt.get(j).getDefects()));
                        System.out.println("Features are - > " + features.get(i));
                        defects.set(i,defects.get(i).concat("," + temp_fBt.get(j).getDefects()));
                        System.out.println("Defects are - > " + defects.get(i));
                    }
                    q_to_update = em.createQuery("DELETE FROM FeatureBugTable fbt where fbt.branchId = :id and fbt.date = :date");
                    q_to_update.setParameter("id", brname.get(0));
                    q_to_update.setParameter("date",new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(i)));
                    em.getTransaction().begin();
                    q_to_update.executeUpdate();
                    em.getTransaction().commit();
                }
                // System.out.println(brname.get(0).getBranchParent().getParentid().getId());
              //  System.out.println("Features are - > " + features.get(i));
                fBg.setBranchId(brname.get(0));                                     //added
                fBg.setParentId(brname.get(0).getBranchParent().getParentid());  //added

                fBg.setFeatures(features.get(i));                         //added
                fBg.setDefects(defects.get(i));                           //added
                
              //  System.out.println("Features are - > " + features.get(i));
                String[] featurebugs = features.get(i).split(",");
                System.out.println("Length of featurebugs->" + featurebugs.length);
                
                String[] engineerDatesList = readexcell.queryByRowKey(featurebugs, "Bug Id", "Deadline");
                String engineerDates = String.join(",", engineerDatesList);
                
                fBg.setColorArray("green");
                for(String Dates : engineerDatesList){
                    System.out.println("dates in milesstones" + Dates);
                    try{
                        if(new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(i)).before(new SimpleDateFormat("yyyy-MM-dd").parse(Dates)))
                            fBg.setColorArray("red");
                    }
                    catch(Exception ex){
                        continue;
                    }
                }
                fBg.setFeaturesdate(engineerDates);                        //added
                fBg.setFeaturecount(featurebugs.length);                          //added
                
                String[] defectbugs = defects.get(i).split(",");
                engineerDatesList = readexcell.queryByRowKey(defectbugs, "Bug Id", "Deadline");
                engineerDates = String.join(",",engineerDatesList);
                
                for(String Dates : engineerDatesList){
                    try{
                        if(new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(i)).before(new SimpleDateFormat("yyyy-MM-dd").parse(Dates)))
                            fBg.setColorArray("red");
                    }
                    catch(Exception ex){
                        continue;
                    }
                }
                fBg.setDefectsdate(engineerDates);                          //added
                fBg.setDefectscount(defectbugs.length);                     //added
                                                                            //added
                
                em.getTransaction().begin();
                em.persist(fBg);
                em.getTransaction().commit();
            }
        closeFactory();
    }
    public void generateFromDb() throws Exception{
        initializeFactory();
        branchList = em.createQuery("SELECT b.branchName from BranchName b").getResultList();
        List<FeatureBugTable> featureBugList = em.createQuery("SELECT f from FeatureBugTable f").getResultList();
        for(FeatureBugTable featureBug : featureBugList){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dates.add(sdf.format(featureBug.getDate()));
            branch.add(featureBug.getBranchId().getBranchName());
            features.add(featureBug.getFeatures());
            defects.add(featureBug.getDefects());
            milestoneId.add(featureBug.getId());
        }
        em.close();
        factory.close();
    }

    @Override
    public void put() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
