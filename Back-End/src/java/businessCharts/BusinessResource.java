/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import businessCharts.entityClasses.BranchName;
import businessCharts.entityClasses.BranchParent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
/**
 * REST Web Service
 *
 * @author ssingh2
 */
@Path("business")
public class BusinessResource extends AbstractBugList{
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BusinessResource
     */
    public BusinessResource() {
    }

    /**
     * Retrieves representation of an instance of businessCharts.BusinessResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("monthlyBar/{branch}")
    @Produces("application/json")
    public String getBar(@PathParam("branch") int branchid) throws Exception{
        //TODO return proper representation object
        BugsChartsGenerate bugsList = new BugsChartsGenerate();
        int month = 0;
        bugsList.generateBarChart(month,branchid);
        bugsList.generatePieChart(branchid);
        String jsonOut = gson.toJson(bugsList);
        return jsonOut;

    } 
    @GET
    @Path("tree")
    @Produces("application/json")
    public String getTree() throws Exception{
        jsontreegenerator jtg = new jsontreegenerator();
        jtg.treeGenerate();
        String jsonOut = gson.toJson(jtg);
        return jsonOut;
    }
    //things to be updated on uploading of new file
    
    @POST
    @Path("upload")
    @Consumes("text/plain")
    public Response onUpload(String data) throws Exception{
        try{
            MilestonesTableEdit mstonedit = new MilestonesTableEdit();
            mstonedit.onUpload();
            BugsListAssignee bugsListAssignee = new BugsListAssignee();
            bugsListAssignee.put();
            BugsListCustomers bugsListCustomers = new BugsListCustomers();
            bugsListCustomers.put();
            BugsListComponent bugsListComponent = new BugsListComponent();
            bugsListComponent.put();
            if(!"".equals(data)){
                InsertBugDb insertBugDb = new InsertBugDb();
                insertBugDb.putData(new SimpleDateFormat("yyyy-MM-dd").parse(data));
            }
            return Response.status(200).entity("Successfully Done").build();
        }
        catch(FileNotFoundException ex){
            return Response.status(200).entity("File not found,Please upload File").build();
        }
    }
    
    @POST
    @Path("putMstones")
    @Consumes("text/plain")
    public Response putMilestones(String data) throws Exception{
        System.out.println("Milestones Data are- > " + data);
        MilestonesTableEdit mtedit = new MilestonesTableEdit();
        mtedit = gson.fromJson(data, MilestonesTableEdit.class);
        mtedit.putDataDb();
        return Response.status(200).entity("Updated").build();
    }
    @GET
    @Path("getMstones")
    @Produces("application/json")
    public String getMstones() throws Exception{
        MilestonesTableEdit mtedit = new MilestonesTableEdit();
        mtedit.generateFromDb();
        String jsonOut = gson.toJson(mtedit);
        return jsonOut;
    }
    @GET
    @Path("getTimeline/{_branchid}")
    @Produces("application/json")
    public String getTimeline(@PathParam("_branchid") int _branchid) throws Exception{
        GenerateMilestonesTLine gMilestonesTLine = new GenerateMilestonesTLine();
        gMilestonesTLine.generateJson(_branchid);
        String jsonOut = gson.toJson(gMilestonesTLine);
        return jsonOut;
    }
    @GET
    @Path("getDependency")
    @Produces("application/json")
    public String getDependency(){
        initializeFactory();
        List<DependencyGenerator> bInfoList = new ArrayList<DependencyGenerator>();
        List<BranchName> brnList = em.createNamedQuery("BranchName.findAll").getResultList();
        
        for(BranchName brn:brnList){
            System.out.println(brn.getBranchName());
            
           /// DependencyGenerator bInfo = new DependencyGenerator(brn.getBranchName(),brn.getId(),brn.getBranchParent().getParentid().getBranchName(),brn.getStatus());
           // bInfoList.add(bInfo)
        }
        for(BranchName brn:brnList){
            Query q = em.createQuery("SELECT b FROM BranchParent b where b.id = :id");
            q.setParameter("id", brn.getId());
            List<BranchParent> brp = q.getResultList();
         //   System.out.println("parent Id - >" + );
            DependencyGenerator bInfo = new DependencyGenerator(brn.getBranchName(),brn.getId(),brp.get(0).getParentid().getBranchName(),brn.getStatus());
            bInfoList.add(bInfo);
        }
        String jsonOut = gson.toJson(bInfoList);
        closeFactory();
        return jsonOut;
    }
    @POST
    @Path("setDependency")
    @Consumes("text/plain")    
    public Response setDependency(String data) throws Exception{
        try{
            System.out.println(data);
            DependencyInsert dpInsert = new DependencyInsert();
            dpInsert = gson.fromJson(data, DependencyInsert.class);
            dpInsert.put();
            return Response.status(200).entity("Dependencies Updated").build();
        }
        catch(FileNotFoundException ex)
        {
            return  Response.status(200).entity("noFile").build();
        }
    }
    /**
     * PUT method for updating or creating an instance of BusinessResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    @Override
    public void put() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
