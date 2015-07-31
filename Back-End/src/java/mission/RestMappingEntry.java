/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import mission.editmission.EditMission;
/**
 * REST Web Service
 *
 * @author SUDHANSHU
 */
/**
 * Hell starts from here,you will go from here
 * @author SUDHANSHU
 */
@Path("mission")
public class RestMappingEntry {
    
    private static EntityManagerFactory factory;
    private static String p_unit_name = "project_manage_dashboardPU" ;
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    @Context
    private UriInfo context;
    /**
     * Creates a new instance of GenericResource
     */
    public RestMappingEntry() {
    }

    /**
     * Retrieves representation of an instance of mission.GenericResource
     * @return an instance of java.lang.String
     * @throws java.io.FileNotFoundException
     */
    @GET
    @Path("getphoto")
    @Produces("image/png")
    public Response getPhoto() throws IOException{
        String fileLocation = "C:\\Users\\Public\\Documents\\Dashboard_External\\missionphoto.jpg";
        BufferedImage bufferedImage  = ImageIO.read(new File(fileLocation));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] imageData = baos.toByteArray();
        return Response.ok(imageData).build();
    }
    @GET
    @Path("all")
    @Produces("application/json")
    public String getJson() throws FileNotFoundException, IOException {
        //TODO return proper representation object
        List<MissionJsonGenerator> sample = new ArrayList<MissionJsonGenerator>();
        
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Public\\Documents\\Dashboard_External\\vision.txt"));
        String vision = br.readLine();
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em =  factory.createEntityManager();
        List<MissionId> list = em.createQuery("SELECT m FROM MissionId m").getResultList();
        List<MissionInfo> minfo;
        for(MissionId id: list){
            Query q = em.createQuery("SELECT m FROM MissionInfo m where m.missionId.id = :name");
            q.setParameter("name", id.getId());
           
            minfo = q.getResultList();
           
            MissionJsonGenerator temp = new MissionJsonGenerator();
            temp.setMissionid(id.getId());
            temp.setMissionname(id.getMissionInfo());
            temp.setMissioncontent(minfo);
            temp.setVision(vision);
            String json = gson.toJson(temp);
            sample.add(temp);   
        }
        String json = gson.toJson(sample);
        System.out.println(json);
        em.close();
        factory.close();
        return json;
    }
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    /**
     * PUT method for updating or creating an instance of GenericResource
     * 
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    @Path("delete")
    @Produces("application/json")
    public List<MissionId> dodelete(){
        factory = Persistence.createEntityManagerFactory(p_unit_name);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("MissionId.findAll");//Query("DELETE FROM MissionId m where m.id = :name");
        List<MissionId> list = q.getResultList();    
        return list;
    }
    @POST
    @Path("edit")
    @Consumes("application/json")
    public Response doEdit(String data){
        EditMission obj;
        try{
            System.out.println(data);
            obj = gson.fromJson(data, EditMission.class);
            obj.updateVision();                                                  //Don't ever try to delete before udpate just beware
            obj.doAddUpdate();
            obj.performDelete();                                                 //otherwise you have to face some seroius issues
            return Response.status(Response.Status.OK).entity("Got it buddy now you go away from my hell").build();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity("exception occurs").build();
    }
    /*
    @POST
    @Path("post")
    @Consumes("application/json")
    public void doPutJson(@Suspended
    final AsyncResponse asyncResponse, final String data) {
        executorService.submit(new Runnable() {
            public void run() {
                asyncResponse.resume(doDoPutJson(data));
            }
        });
    }
    private Response doDoPutJson(String data) {
        System.out.println("Inside do put json");
        System.out.println(data);
        //java.lang.reflect.Type listofDelMission = new TypeToken<List<DelMissionP>>(){}.getType();            //To let know gson to what type of list generated
        DelMissionP perform = gson.fromJson(data, DelMissionP.class);
        System.out.println(perform.getMid());
        System.out.println(perform.getPid());
        perform.performDelete();
        return Response.status(200).entity("Got it buddy").build();
    }*/
}