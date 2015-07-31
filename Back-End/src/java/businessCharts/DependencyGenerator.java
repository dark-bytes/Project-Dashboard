/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import com.google.gson.annotations.Expose;

/**
 *
 * @author ssingh2
 */
public class DependencyGenerator {
    @Expose
    private String branchname;
    @Expose 
    private Integer branchid;
    @Expose
    private String parentname;
    @Expose
    private String status;
    DependencyGenerator(String branchname,Integer branchid,String parentname,String status){
        this.branchname = branchname;
        this.branchid = branchid;
        this.parentname = parentname;
        this.status = status;
    }
    String getBranchName(){
        return this.branchname;
    }
    Integer getBranchId(){
        return this.branchid;
    }
    String getParentName(){
        return this.parentname;
    }
    String getStatus(){
        return this.status;
    }
}
