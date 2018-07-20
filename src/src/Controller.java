import javax.swing.tree.DefaultMutableTreeNode;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lesliecovarrubias on 7/14/18.
 */
public class Controller extends DefaultMutableTreeNode {
    private List<User> users;
    private List<UserGroup> groups;
    private List<Component> components = new ArrayList<>();
    private Set<String> uniqueIds = new HashSet<>();

    private int totalMessages;
    private int totalPositiveMessages;
    private String valid;

    private String time;


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
    public String geTime(){
        return time;
    }
    public String getValid(){
        return valid;
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
    public boolean checkFormat(String id) {
        if (id.contains(" ")) {
            return false;
        }
        return true;
    }

    public String validate(User use){
        if(use.getValid()){
            return "Valid";
        }
        return "Invalid";
    }

    public boolean duplicate(String id){
        if(uniqueIds.contains(id)){
            return false;
        }
        uniqueIds.add(id);
        return true;

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
                time = userComponent.getLastUpdateTime();
                visit(userComponent);
                valid = validate(userComponent);
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

    public void visit(UserGroup group) {
        String groupId = group.getId();
        duplicate(groupId);
        checkFormat(groupId);
    }

    public void visit(User user) {
        String userId = user.getUserId();
        user.setValid(duplicate(userId));
        user.setValid(checkFormat(userId));
    }


}
