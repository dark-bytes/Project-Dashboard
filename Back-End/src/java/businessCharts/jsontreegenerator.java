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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.TreeMap;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ssingh2
 */
public class jsontreegenerator {
    EntityManagerFactory factory;
    String p_unit_name = "project_manage_dashboardPU";
    
    @Expose
    Node root;
    
    
    public static class Node{
        public String name;
        public List<Node> children;
        public Integer id;
        public Integer activecount;
        public String active;
        Node(String rootData,Integer id,Integer activecount,String active) {
            this.name = rootData;
            this.id = id;
            this.activecount = activecount;
            this.active = active; 
            this.children = new ArrayList<Node>();
        }
        public List<Node> getchildren(){
            return this.children;
        }
    }
    
    
    
    public void treeGenerate(){
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
   //     TreeMap< Integer, TreeMap< Integer, TreeMap<Integer,Integer> > > tmap = new TreeMap<Integer, TreeMap< Integer, TreeMap<Integer,Integer> > >();
        
        List<BranchName> brname = new ArrayList<BranchName>();
        List<BranchParent> brparent = new ArrayList<BranchParent>();
        brname = em.createNamedQuery("BranchName.findAll").getResultList();
        brparent = em.createNamedQuery("BranchParent.findAll").getResultList();
        int count = brparent.size();
        List< Node > Tree = new ArrayList<>(count);
        Queue< Node > queue = new LinkedList< Node > ();
        for(BranchParent brp : brparent){
            Node temp = new Node(brp.getBranchName().getBranchName(),brp.getId(),0,brp.getBranchName().getStatus());
            Tree.get(brp.getParentid().getId()).getchildren().add(temp);
            Tree.set(brp.getId(), temp);
        }
        for(Node tree : Tree){
            System.out.println("Root" + tree.id);
            List< Node > arr = new ArrayList<Node>();
            arr = tree.getchildren();
            for(Node child : arr){
                System.out.print(child.id + " ");
            }
        }
    }
}
