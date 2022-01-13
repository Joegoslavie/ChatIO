package websockets;

import models.User;

import java.util.List;

public class CreateGroupEvent {

    private String name;
    private boolean isPrivate;
    private List<User> members;

    public String getName(){
        return this.name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<User> getMembers() {
        return members;
    }

    public CreateGroupEvent(String name, Boolean isPrivate, List<User> users){
        this.name = name;
        this.isPrivate = isPrivate;
        this.members = users;
    }

    public CreateGroupEvent(){

    }
}
