
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserView extends JFrame {
    JFrame frame;
    AdminPanel adminPanel;
    JList<User> topPanelList;
    DefaultListModel<User> userModel;
    JList bottomPanelList;
    DefaultListModel<Message> newsfeedModel;
    JScrollPane topPane;
    JScrollPane bottomPane;

    private JButton followUser;
    private JButton postTweet;
    private JTextField userId;
    private JTextField twitterMessage;
    int userCount = 0;

    User user;

    UserView(){
        ui();
    }

    UserView(User user){
        this.user = user;
        System.out.println("User Count: " + ++userCount);
        System.out.println("User ID opened is: " + user.getUserId());
        adminPanel = AdminPanel.getInstance();
        ui();
    }

    private void ui(){
        texts();
        lists();
        buttons();
        frames();
    }

    public void frames(){
        frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("@" + user.getUserId());
        frame.setResizable(false);
        frame.setLayout(null);

        frame.add(userId);
        frame.add(followUser);

        frame.add(topPane);
        frame.add(bottomPane);

        frame.add(twitterMessage);
        frame.add(postTweet);

        frame.setSize(505,640);
        frame.setVisible(true);
    }

    public void lists(){
        topPanelList = new JList<>();
        topPanelList.setName("Current Following");
        userModel = new DefaultListModel<>();
        topPanelList.setModel(userModel);
        topPane = new JScrollPane(topPanelList);
        topPane.setBounds(5,45,495,275);

        createUserModel();

        bottomPanelList = new JList();
        bottomPanelList.setName("News Feed");
        newsfeedModel = new DefaultListModel<>();
        bottomPanelList.setModel(newsfeedModel);
        bottomPane = new JScrollPane(bottomPanelList);
        bottomPane.setBounds(5,370,495,245);
        createNewsFeedModel();
    }


    public void texts(){
        userId = new JTextField();
        userId.setBounds(5,5,250, 35);
        twitterMessage = new JTextField();
        twitterMessage.setBounds(5, 325, 250, 35);
    }



    public void buttons(){
        followUser = new JButton();
        followUser.setText("Follow User");
        followUser.setBounds(255, 5, 245, 35);
        followUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                followUser();
            }
        });

        postTweet = new JButton();
        postTweet.setText("Post Tweet");
        postTweet.setBounds(255, 325, 245, 35);
        postTweet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postUserTweet();
            }
        });
    }

    public void postUserTweet(){
        String message = twitterMessage.getText();
        this.user.tweet(message);
        twitterMessage.setText("");
        createNewsFeedModel();
    }

    public void followUser(){
        String twitterUser = userId.getText();
        if(twitterUser.trim().equals("")){
            JOptionPane.showMessageDialog(frame, "Error: Enter a user ID !");
            userId.setText("");
            return;
        }

        if(twitterUser.equals(user.getUserId())){
            JOptionPane.showMessageDialog(frame, "Error: Can't follow yourself !");
            userId.setText("");
            return;
        }
        if(adminPanel.userController.checkUserExists(twitterUser)){
            User followingUser = adminPanel.userController.getUser(twitterUser);
            user.follow(followingUser);
            createUserModel();
            followingUser.follow(user);
            System.out.println(user.getUserId() + " is now following " + twitterUser );
        }else{
            JOptionPane.showMessageDialog(frame, "Error: User does not exist");
        }

        userId.setText("");
    }

    public void createUserModel(){
        userModel.clear();
        List<User> currentFollowings = user.getFollowing();
        if(currentFollowings == null){
            System.out.println("You are currently not following anyone");
        }
        for(User x: currentFollowings){
            userModel.addElement(x);
        }
    }

    public void createNewsFeedModel(){
        newsfeedModel.clear();
        List<Message> currentFeed = user.getNewsfeed();
        if(currentFeed.size() == 0){
            System.out.println("No activity in news feed");
            return;
        }
        for(Message x: currentFeed){
            newsfeedModel.addElement(x);
        }
    }

}
