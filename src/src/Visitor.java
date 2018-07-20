import java.security.acl.Group;

/**
 * Created by lesliecovarrubias on 7/14/18.
 */
public class Visitor {
    private int userCount = 0;
    private int groupCount = 0;


    void visit (User use){
        userCount++;
    }
    void visit (UserGroup grp){
        groupCount++;
    }

    public int getUserCount(){
        return userCount;
    }

    public int getGroupCount(){
        return groupCount;
    }

}
