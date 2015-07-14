/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author ssingh2
 */
@Path("business")
public class BusinessResource {
    private static EntityManagerFactory factory;
    private static String p_unit_name = "project_manage_dashboardPU" ;
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
    @Path("monthlyBar/{month}/{branch}")
    @Produces("application/json")
    public String getBar(@PathParam("month") int month ,@PathParam("branch") int branchid) {
        //TODO return proper representation object
        BugsListGraph bugsList = new BugsListGraph();
        bugsList.generateList(month,branchid);
        String jsonOut = gson.toJson(bugsList);
        return jsonOut;
    }
    @GET
    @Path("")
    /**
     * PUT method for updating or creating an instance of BusinessResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
