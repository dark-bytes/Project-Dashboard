/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission.editmission;

import com.google.common.io.FileBackedOutputStream;
import com.google.gson.annotations.Expose;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import mission.MissionId;
import mission.MissionInfo;

/**
 *
 * @author SUDHANSHU
 */
public class EditMission {
    
    private static EntityManagerFactory factory;
    private static EntityManager em;
    private static String p_unit_name = "project_manage_dashboardPU" ;
    private Map<Integer,MissionId> mapmission;
    private Map<Integer,MissionInfo> mappoint;
    @Expose 
    private String vision;
    @Expose
    private List<Integer> deleteMissionId;// = new ArrayList<Integer>();
    @Expose
    private List<Integer> deletePointId;// = new ArrayList<Integer>();
//    @Expose
//    private List<Integer> addmId;// = new ArrayList<Integer>();
//    @Expose
//    private List<String> addpValue;// = new ArrayList<String>();
    @Expose
    private List<Update> Update;// = new ArrayList<Update>();
 
    private void initializeFactory(){
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        em = factory.createEntityManager();
    }
    private void closeFactory(){
        em.close();
        factory.close();
    }
    public void performDelete(){
        try{
            try{
                System.out.println("Size of deletemisssionid" + deleteMissionId.size());
                System.out.println(deletePointId.size());
                
                initializeFactory();
                em.getTransaction().begin();                              //Important code used for deleting transaction to begin 
                Query q;
                
                for(int i = 0;i < deleteMissionId.size();i++){
                    if(deleteMissionId.get(i) < 0)
                        deleteMissionId.set(i, mapmission.get(deleteMissionId.get(i)).getId());
                }
                
                for(int i = 0;i < deletePointId.size();i++){
                    if(deletePointId.get(i) < 0)
                        deletePointId.set(i,mappoint.get(deletePointId.get(i)).getId());
                }
                
                if(deleteMissionId.size() > 0){
                    q = em.createQuery("DELETE FROM MissionId m where m.id IN :par",MissionId.class);
                    q.setParameter("par", deleteMissionId); 
                    System.out.println("Number of rows deleted(mission)" + q.executeUpdate());
                }
                if(deletePointId.size() > 0){
                    q = em.createQuery("DELETE FROM MissionInfo m where m.id IN :par",MissionInfo.class);
                    q.setParameter("par", deletePointId);
                    System.out.println("Number of rows deleted(points)" + q.executeUpdate());
                }
                
                em.getTransaction().commit();               //transaction to commit 
                closeFactory();
            }
            catch(Exception ex){                
                System.out.println("Errors you got" + Arrays.toString(ex.getStackTrace()));
            }
        }
        catch(OutOfMemoryError em){
            System.out.println("Out of memmory buddy rerun server");
        }
    }
    public void doAddUpdate(){
        mapmission = new TreeMap<Integer, MissionId>();
        mappoint = new TreeMap<>();
        for(Update temp:Update){
            try{
                if(temp.getAdd() == 0){
                    if(temp.getWhat().equals("mission"))                                   //mission added or updated
                        doMissionUpdate(temp);
                    
                    else
                        doPointUpdate(temp);
                    
                }
                else{
                    if(temp.getWhat().equalsIgnoreCase("mission"))
                        doMissionAdd(temp);
                    else
                        doPointAdd(temp);
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
                System.out.println("Exception occurs");
            }
        } 
    }
    public void doMissionUpdate(Update temp) throws Exception{
        initializeFactory();
        Query q = em.createQuery("UPDATE MissionId m set m.missionInfo = :setval where m.id = :par");  
        em.getTransaction().begin();
        
        if(temp.getId() > 0){
            q.setParameter("par", temp.getId());
            q.setParameter("setval", temp.getValue());                       //System.out.println(q.executeUpdate());
        }
        else{
            q.setParameter("par", mapmission.get(temp.getId()).getId());
            q.setParameter("setval", temp.getValue());
        }
        
        q.executeUpdate();
        em.getTransaction().commit();
        closeFactory();
    }
    public void doPointUpdate(Update temp) throws Exception{
        initializeFactory();
        em.getTransaction().begin();
        Query q = em.createQuery("UPDATE MissionInfo m set m.info = :setval where m.id = :par");
        
        if(temp.getId() > 0){ 
            q.setParameter("par", temp.getId());
            q.setParameter("setval", temp.getValue());
        }
        else{
            q.setParameter("par", mappoint.get(temp.getId()).getId());
            q.setParameter("setval", temp.getValue());
        }
        
        q.executeUpdate();
        em.getTransaction().commit();
        closeFactory();
    }
    public void doMissionAdd(Update temp) throws Exception{
        initializeFactory();
        em.getTransaction().begin();
        MissionId mId = new MissionId();
        mId.setMissionInfo(temp.getValue());
        em.persist(mId);
        em.getTransaction().commit();
        //Integer value = mId.getId();
        mapmission.put(temp.getId(), mId);
        
        closeFactory();
        //System.out.println("Id alloted will be" + value);
    }
    public void doPointAdd(Update temp) throws Exception{
        try{
            initializeFactory();
            
            MissionInfo m = new MissionInfo();
            if(temp.getMid() > 0){
               // System.out.println(temp.);      
                Query q = em.createQuery("SELECT m FROM MissionId m where m.id = :par");
                q.setParameter("par", temp.getMid());
                List<MissionId> m_to_add = q.getResultList();
                System.out.println("Count of mission to be added" + m_to_add.size());
                m.setMissionId(m_to_add.get(0));
                m.setInfo(temp.getValue());
                em.getTransaction().begin();
                em.persist(m);
                em.getTransaction().commit();
                if(temp.getId() < 0)
                    mappoint.put(temp.getId(),m);
            }
            else{
                m.setInfo(temp.getValue());
                m.setMissionId(mapmission.get(temp.getMid()));
                em.getTransaction().begin();
                em.persist(m);
                em.getTransaction().commit();
                mappoint.put(temp.getId(),m);
            }
            
            closeFactory();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void updateVision(){
            try {
                File path = new File("").getAbsoluteFile();
                if(vision.length() > 0 || vision != null){
                    System.out.println(path);
                    FileWriter writer = new FileWriter("C:\\Users\\Public\\Documents\\Dashboard_External\\vision.txt");
                    BufferedWriter br = new BufferedWriter(writer);
                    br.write(vision);
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(EditMission.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }
    
    public List<Integer> getDeleteId() {
        return deleteMissionId;
    }

    /**
     * 
     * @param deleteId
     *     The deleteId
     */
    public void setDeleteId(List<Integer> deleteId) {
        this.deleteMissionId = deleteId;
    }

    /**
     * 
     * @return
     *     The pointId
     */
    public List<Integer> getPointId() {
        return deletePointId;
    }

    /**
     * 
     * @param pointId
     *     The pointId
     */
    public void setPointId(List<Integer> pointId) {
        this.deletePointId = pointId;
    }

    /**
     * 
     * @return
     *     The addmId
     */
//    public List<Integer> getAddmId() {
//        return addmId;
//    }
//
//    /**
//     * 
//     * @param addmId
//     *     The addmId
//     */
//    public void setAddmId(List<Integer> addmId) {
//        this.addmId = addmId;
//    }

//    /**
//     * 
//     * @return
//     *     The addpValue
//     */
//    public List<String> getAddpValue() {
//        return addpValue;
//    }
//
//    /**
//     * 
//     * @param addpValue
//     *     The addpValue
//     */
//    public void setAddpValue(List<String> addpValue) {
//        this.addpValue = addpValue;
//    }

    /**
     * 
     * @return
     *     The Update
     */
    public List<Update> getUpdate() {
        return (List<mission.editmission.Update>) Update;
    }

    /**
     * 
     * @param Update
     *     The Update
     */
    public void setUpdate(List<Update> Update) {
        this.Update = Update;
    }

    /**
     * 
     * @return
     *     The AddMissionInfo
     */
//    public List<AddMissionInfo> getAddMissionInfo() {
 //       return (List<mission.editmission.AddMissionInfo>) AddMissionInfo;
  //  }

    /**
     * 
     * @param AddMissionInfo
     *     The AddMissionInfo
     */
 //   public void setAddMissionInfo(List<AddMissionInfo> AddMissionInfo) {
  //      this.AddMissionInfo = AddMissionInfo;
 //   }

}