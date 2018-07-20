import java.util.ArrayList;
import java.util.List;

public class UserGroup implements Visitable, Component {
    private String groupId;
    private List<Component> components = new ArrayList<>();

    public UserGroup(String groupId){
        this.groupId = groupId;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
        if(components != null){
            for (Component i: components){
                if(components instanceof UserGroup){
                    ((UserGroup) components).accept(v);
                }
                else {
                    ((User) components).accept(v);
                }
            }
        }
    }


    @Override
    public String  getId() {
        return this.groupId;
    }

    @Override
    public void addUserComponent(Component newComponent) {
        components.add(newComponent);
    }
}
