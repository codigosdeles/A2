import java.util.ArrayList;
import java.util.List;

/**
 * Created by lesliecovarrubias on 7/14/18.
 */
public class Message {
    public String msg;
    boolean postive;

    public Message (){
        this.msg = "";
        this.postive = false;
    }
    public Message (String msg){
        setMessage(msg);
    }

    public void setMessage(String message){
        this.msg = message;
        this.postive = isPositive();
    }

    public String getMessage(){
        return this.msg;
    }

    public boolean getPositive(){
        return this.postive;
    }


    public boolean isPositive(){
        String temp1 = "happy";
        String temp2 = "great";
        String temp3 = "yay";
        String temp4 = "good";

        String str = this.msg.toLowerCase();

        if(str.contains(temp1.toLowerCase()) || str.contains(temp2.toLowerCase()) || str.contains(temp3.toLowerCase()) || str.contains(temp4.toLowerCase())){
            return true;
        }
        return false;
    }


}
