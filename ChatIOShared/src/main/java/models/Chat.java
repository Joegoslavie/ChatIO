package models;

import java.io.Serializable;
import java.util.Date;

public class Chat extends Conversation implements Serializable {

    private String localUsername;

    public Chat(int id, Date createdAt){
        super(id, createdAt);
    }

    public Chat(){

    }

    public void setLocalUsername(String username){
        this.localUsername = username;
    }

    @Override
    public String toString(){
        for(User usr : super.getMembers()){
            if(!usr.getUsername().equals(localUsername)){
                return usr.getUsername();
            }
        }

        return "Chat";
    }
}
