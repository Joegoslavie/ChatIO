package models;

import java.util.Date;
import java.util.List;

public class Conversation {

    private int id;
    private Date createdAt;
    private List<User> members;
    private List<Message> messages;

    public int getId(){
        return this.id;
    }

    public Date getCreatedAt(){
        return this.createdAt;
    }

    public List<User> getMembers(){
        return this.members;
    }

    public List<Message> getMessages(){
        return this.messages;
    }

    public void setMembers(List<User> members){
        this.members = members;
    }

    public void setMessages(List<Message> messages){
        this.messages = messages;
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public Conversation(int id, Date createdAt){
        this.id = id;
        this.createdAt = createdAt;
    }

    public Conversation(){

    }
}
