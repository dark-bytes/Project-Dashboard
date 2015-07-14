package mission;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mission.MissionInfo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-14T16:18:23")
@StaticMetamodel(MissionId.class)
public class MissionId_ { 

    public static volatile CollectionAttribute<MissionId, MissionInfo> missionInfoCollection;
    public static volatile SingularAttribute<MissionId, String> missionInfo;
    public static volatile SingularAttribute<MissionId, Integer> id;

}