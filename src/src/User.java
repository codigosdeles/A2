import java.util.List;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;



public class User extends Observable implements Visitable, Observer, Component {
    private String id;
    private List<User> followers;
    private List<User> following;
    private List<Message> feed;
    private int messageCount;
    private int positiveCount;
    private Message message;
    private long lastUpdateTime;
    private long creationTime;
    private boolean valid;

    public User(String id){
        this.id = id;
        followers = new ArrayList<>();
        following = new ArrayList<>();
        feed = new ArrayList<>();
        messageCount = 0;
        positiveCount = 0;
        valid = true;
    }

    public void setId(String id){
        this.id = id;
    }
    public void setValid(boolean val){
        this.valid = val;
    }
    public int getMessageCount(){
        return messageCount;
    }

    public String getUserId() {
        return id;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<Message> getNewsfeed() {
        return feed;
    }

    public String getCreationTime(){
        Date currentDate = new Date(creationTime);
        System.out.println("Creation time stamp:");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        return dateFormat.format(currentDate);
    }

    public void tweetTimeStamp(){
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public String getTimeStamp(){
        Date currentDate = new Date(lastUpdateTime);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        return dateFormat.format(currentDate);
    }

    //adds to following group
    public void follow(User user){
        following.add(user);
        addObserver(user);

    }

    public String getMessage(){
        return message.msg;
    }

    public void tweet(String msg){
        String temp = getUserId() + ": " + msg;
        message = new Message(temp);
        feed.add(message);
        tweetTimeStamp();
        this.messageCount ++;
        updatePositiveCount(message);
        notifyObservers(msg);
    }

    private void updatePositiveCount(Message msg){
        if(msg.getPositive()){
            positiveCount ++;
        }
    }

    public int getPositiveTotal(){
        return positiveCount;
    }
    public boolean getValid(){
        return valid;
    }

    public String getLastUpdateTime(){
        return getTimeStamp();
    }

    @Override
    public void accept(Visitor vtor) {
        vtor.visit(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof User){
            System.out.println("Tweet by" + ((User) o).getUserId()+ "sucessful");
            tweetTimeStamp();
        }
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void addUserComponent(Component newComponent) {
        //intended blank
    }
}
