/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.Allopenbugs;
import com.google.gson.annotations.Expose;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ssingh2
 */
public class BugsListGraph {
    private EntityManagerFactory factory;
    private String p_unit_name = "project_manage_dashboardPU";    
    @Expose
    List<Integer> open = new ArrayList<Integer>();
    @Expose
    List<Integer> cloned = new ArrayList<Integer>();
    @Expose 
    List<Date> dates = new ArrayList<Date>();
    @Expose
    List<String> names = new ArrayList<String>();
    @Expose
    List<String> assignee = new ArrayList<String>();
    @Expose
    List<Integer> assignedBugs = new ArrayList<Integer>();
    
    void generateList(int month,int name) {
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("SELECT a FROM Allopenbugs a WHERE a.allopenbugsPK.date between :date1 and :date2");        
        Calendar myCal = Calendar.getInstance();
        myCal.set(Calendar.MONTH, month);
        myCal.set(Calendar.DAY_OF_MONTH, 1);
        Date d = myCal.getTime();
        System.out.println(d);
        q.setParameter("date1", d);
        myCal.set(Calendar.MONTH, month);
        myCal.set(Calendar.DAY_OF_MONTH, 31);
        d = myCal.getTime();
        System.out.println(d);
        q.setParameter("date2", d);
        List<Allopenbugs> list = q.getResultList();
        for(Allopenbugs l : list){
            if(l.getAllopenbugsPK().getBranchId() == name){
                open.add(l.getOpenCloned());
                cloned.add(l.getOpenNotCloned());
                dates.add(l.getAllopenbugsPK().getDate());
            }
        }
        em.close();
        factory.close();
    }
    
    void generatePie(int name){
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
       // Query q = em.createQuery("SELECT a FROM Allopenbugs a where a.")
    }
}
