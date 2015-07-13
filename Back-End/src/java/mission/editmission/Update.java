/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission.editmission;

import com.google.gson.annotations.Expose;

/**
 *
 * @author SUDHANSHU
 */
public class Update {

    @Expose
    private String what;
    @Expose
    private Integer id;
    @Expose
    private String value;
    @Expose
    private Integer mid; 
    @Expose 
    private Integer add;
    /**
     * 
     * @return
     *     The what
     */
    public String getWhat() {
        return what;
    }
  
    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }
    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }
      
    public Integer getMid(){
        return this.mid;
    }   
    public Integer getAdd(){
        return this.add;
    }
}
