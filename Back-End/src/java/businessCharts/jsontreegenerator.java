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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.TreeMap;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ssingh2
 */
public class jsontreegenerator {
    EntityManagerFactory factory;
    String p_unit_name = "project_manage_dashboardPU";
    
    @Expose
    Node root = new Node("root",0,0,"na");
        
    public static class Node{
        @Expose
        public String name;
        @Expose
        public Integer branchid;
        @Expose
        public String active;
        @Expose
        public Integer activecount;        
        @Expose
        public List<Node> children;
        
        Node(String rootData,Integer id,Integer activecount,String active) {
            this.name = rootData;
            this.branchid = id;
            this.activecount = activecount;
            this.active = active; 
            this.children = new ArrayList<Node>();
        }
        public List<Node> getchildren(){
            return this.children;
        }
        public void print(){
       //     System.out.println(name + branchid + activecount + active);
        }
    }
    Node dfs(List<Node> Tree,Integer index) throws Exception{
        Node tree = new Node(Tree.get(index).name,Tree.get(index).branchid,Tree.get(index).activecount,Tree.get(index).active);
        int active = 0;
        for(Node child : Tree.get(index).children){
            Node temp = dfs(Tree,child.branchid);
            active += temp.activecount;
            tree.children.add(temp);
        }
        if("true".equals(tree.active)){
            tree.activecount = tree.activecount + active + 1;
        }
        else
            tree.activecount += active;
        return tree;
    }
    
    public void treeGenerate() throws Exception{
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
   //     TreeMap< Integer, TreeMap< Integer, TreeMap<Integer,Integer> > > tmap = new TreeMap<Integer, TreeMap< Integer, TreeMap<Integer,Integer> > >();
        
        List<BranchParent> brparent = new ArrayList<BranchParent>();
        brparent = em.createQuery("SELECT b from BranchParent b").getResultList();
        Object obj = em.createQuery("SELECT max(b.id) FROM BranchName b").getSingleResult();
        int count = ((Number) obj).intValue();
  //      System.out.println(count);
        List< Node > Tree = Arrays.asList(new Node[count + 1]);
        Queue< Integer > queue = new LinkedList< Integer > ();
        for(BranchParent brp : brparent){
            List<BranchName> brname = new ArrayList<BranchName>();
            Query q = em.createQuery("SELECT b FROM BranchName b where b.id = :id");
            q.setParameter("id", brp.getId());
            brname = q.getResultList();
            System.out.println(brname.get(0).getBranchName() + brname.get(0).getStatus());
            Node temp = new Node(brname.get(0).getBranchName(),brp.getId(),0,brname.get(0).getStatus());
            Tree.set(brp.getId(), temp);
        }
 //       System.out.println("Size is" + Tree.size());
        for(BranchParent brp : brparent){
            List<BranchName> brname = new ArrayList<BranchName>();
            Query q = em.createQuery("SELECT b FROM BranchName b where b.id = :id");
            q.setParameter("id", brp.getId());
            brname = q.getResultList();
            System.out.println(brname.get(0).getBranchName() + brname.get(0).getStatus());
            Node temp = new Node(brname.get(0).getBranchName(),brp.getId(),0,brname.get(0).getStatus());
       //     Node temp = new Node(brp.getBranchName().getBranchName(),brp.getId(),0,brp.getBranchName().getStatus());            
            if(Objects.equals(brp.getId(), brp.getParentid().getId())){
                queue.add(brp.getId());
            }
            else{//System.out.println(brp.getParentid().getId());
                Tree.get(brp.getParentid().getId()).children.add(temp);
                
            }
        }
        while(!queue.isEmpty()){
            Node temp = dfs(Tree,queue.remove());
            System.out.println(temp.branchid);
            root.activecount += temp.activecount;
            root.children.add(temp);
        }
        em.close();
        factory.close();
    }
}
