/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission;

import com.google.gson.annotations.Expose;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

/**
 
 * @author SUDHANSHU
 */
public class DelMissionP {
    private static EntityManagerFactory factory;
    private static String p_unit_name = "project_manage_dashboardPU" ;
    
    @Expose
    private List<Integer> mission_id;
    @Expose
    private List<Integer> point_id;
    public void setMid(List<Integer> id){
        this.mission_id = id;
    }
    public List<Integer> getMid(){
        return this.mission_id;
    }
    public void setPid(List<Integer> pid){
        this.point_id = pid;
    }
    public List<Integer> getPid(){
        return this.point_id;
    }
    public void performDelete(){
        
    }
}
