import javax.swing.tree.DefaultMutableTreeNode;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lesliecovarrubias on 7/14/18.
 */
public class Controller extends DefaultMutableTreeNode {
    private List<User> users;
    private List<UserGroup> groups;
    private List<Component> components = new ArrayList<>();

    private int totalMessages;
    private int totalPositiveMessages;


    public  Controller(){
        users = new ArrayList<>();
        groups = new ArrayList<>();
        totalMessages = 0;
        totalPositiveMessages = 0;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public int getTotalMessages(){
        return totalMessages;
    }
    public int getTotalPositiveMessages(){
        return totalPositiveMessages;
    }
    public double calculatePositivePercent(){
        double percent = totalPositiveMessages / totalMessages;
        return percent;

    }

    public void addLeaf(String userId, DefaultMutableTreeNode root){
        User user = new User(userId);
        DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(user);
        newUser.setAllowsChildren(false);
        root.add(newUser);
        users.add(user);
    }

    public void addGroup(String groupId, DefaultMutableTreeNode root){
        UserGroup temp = new UserGroup(groupId);
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(temp);
        newGroup.setAllowsChildren(true);
        root.add(newGroup);
        groups.add(temp);
    }

    public void addGroupToGroup(String groupId, DefaultMutableTreeNode selectGrp){
        UserGroup temp = new UserGroup(groupId);
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(temp);
        newGroup.setAllowsChildren(true);
        selectGrp.add(newGroup);
        Group grabGroup = (Group)selectGrp.getUserObject();
        groups.add(temp);

    }

    public void addLeafToGroup(String userId, DefaultMutableTreeNode selectGrp){
        UserGroup grpObj= (UserGroup)selectGrp.getUserObject();
        User temp = new User(userId);
        DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(temp);
        newUser.setAllowsChildren(false);
        selectGrp.add(newUser);
        grpObj.addUserComponent(temp);
        users.add(temp);
    }

    public User getUser(String userId){
        for(User i: users){
            if(i.getUserId().equals(userId)){
                return i;
            }
        }
        return null;
    }

    public void iterateComponents(Visitor v){
        for(Component component: components){
            if(component instanceof UserGroup){
                UserGroup groupComponent = (UserGroup)component;
                groupComponent.accept(v);
            }else{
                User userComponent = (User)component;
                totalMessages = userComponent.getMessageCount();
                totalPositiveMessages = userComponent.getPositiveTotal();
                userComponent.accept(v);
            }
        }
    }

    public boolean checkGroupExists(String groupId){
        for(UserGroup i: groups){
            if(i.getId().equals(groupId)){
                return true;
            }
        }
        return false;
    }

    public boolean checkUserExists(String userId){
        for(User i: users){
            if(i.getUserId().equals(userId)){
                return true;
            }
        }
        return false;
    }






}
