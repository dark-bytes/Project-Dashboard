/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 *
 * @author SUDHANSHU
 */
public class MissionJsonGenerator {
    @Expose 
    private String vision;
    @Expose
    private int id;
    @Expose
    private String missionInfo;
    @Expose
    private List<MissionInfo> missioncontent;
    public void setVision(String vision){
        this.vision = vision;
    }
    public String getMissionname ()
    {
        return missionInfo;
    }

    public void setMissionname (String missionname)
    {
        this.missionInfo = missionname;
    }

    public List<MissionInfo> getMissioncontent ()
    {
        return missioncontent;
    }

    public void setMissioncontent (List<MissionInfo> missioncontent)
    {
        this.missioncontent = missioncontent;
    }

    public int getMissionid ()
    {
        return id;
    }

    public void setMissionid (int missionid)
    {
        this.id = missionid;
    }
     @Override
    public String toString()
    {
        return "ClassPojo [missionname = "+missionInfo+", missioncontent = "+missioncontent+", missionid = "+ id +"]";
    }
}
