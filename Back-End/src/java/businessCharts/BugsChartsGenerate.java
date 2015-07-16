/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.Allopenbugs;
import businessCharts.entityClasses.BranchParent;
import com.google.gson.annotations.Expose;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class BugsChartsGenerate {
    private EntityManagerFactory factory;
    private String p_unit_name = "project_manage_dashboardPU";    
    @Expose
    List<Integer> open = new ArrayList<>();
    @Expose
    List<Integer> cloned = new ArrayList<>();
    @Expose 
    List<Date> dates = new ArrayList<>();
    @Expose
    List<String> names = new ArrayList<>();
    @Expose
    List<String> assignee = new ArrayList<>();
    @Expose
    List<Integer> assignedclonedBugs = new ArrayList<>();
    @Expose
    List<Integer> assignedopenedBugs = new ArrayList<>();
    @Expose
    List<Integer> customerclonedBugs = new ArrayList<>();
    @Expose
    List<Integer> customeropenedBugs = new ArrayList<>();
    @Expose 
    List<String> customers = new ArrayList<>();
    @Expose
    List<String> components = new ArrayList<>();
    @Expose
    List<Integer> componentclonedBugs = new ArrayList<>();
    @Expose
    List<Integer> componentopenedBugs = new ArrayList<>();
    
    
    void generateBarChart(int month,int name) {
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
   
    void generatePieChart(int name){
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("SELECT b from BranchParent b");
        List<BranchParent> brp = q.getResultList();
        List<Integer> list = new ArrayList<Integer>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(name);
        list.add(name);
        while(!queue.isEmpty()){
           int top = queue.element();
            queue.remove();
         //   System.out.println("top" + top);
            for(BranchParent bp : brp){
               // System.out.println(bp.getParentid().getId());
                if(bp.getParentid().getId() == top && bp.getId() != top){
                    System.out.println(bp.getParentid().getId());
                    queue.add(bp.getId());
                    list.add(bp.getId());
                }
            }
        }
        //Assignee Pie Chart
        q = em.createQuery("SELECT a.assigneeName,SUM(a.clonedBugs) AS bugs,SUM(a.openBugs) AS openbugs FROM AssigneeData a where a.branchid IN :id GROUP BY a.assigneeName");
        q.setParameter("id", list);
        List<Object[]> results = q.getResultList();
        for (Object[] result : results) {
            assignee.add((String) result[0]);
            assignedclonedBugs.add(((Number) result[1]).intValue());
            assignedopenedBugs.add(((Number) result[2]).intValue());
        }
        
        //Component Pie CHart
        q = em.createQuery("SELECT c.componentList,SUM(c.cloned) AS bugs,SUM(c.open) AS openbugs FROM ComponentList c where c.branchId IN :id GROUP BY c.componentList");
        q.setParameter("id", list);
        results = q.getResultList();
        for(Object[] result : results){
            components.add((String)result[0]);
            componentclonedBugs.add(((Number)result[1]).intValue());
            componentopenedBugs.add(((Number)result[2]).intValue());
        }
        
        //Customer Pie chart
        q = em.createQuery("SELECT c.customerName,SUM(c.clonedbugs) AS bugs,SUM(c.openbugs) AS openbugs FROM CustomerList c where c.branchId IN :id GROUP BY c.customerName");
        q.setParameter("id", list);
        results = q.getResultList();
        for(Object[] result: results){
            customers.add((String)result[0]);
            customerclonedBugs.add(((Number)result[1]).intValue());
            customeropenedBugs.add(((Number)result[2]).intValue());
        }
    }
}
