import java.security.acl.Group;

/**
 * Created by lesliecovarrubias on 7/14/18.
 */
public interface Component {
    public String getId();
    public void addUserComponent(Component newComponent);
}