package models;

import java.io.Serializable;
import java.util.Date;

public class Group extends Conversation implements Serializable {

    private String name;
    private boolean isPrivate;
    private int ownerId;

    private User owner;

    public Group(int id, String name, boolean priv, int ownerId, Date created){
        super(id, created);
        this.name = name;
        this.isPrivate = priv;
        this.ownerId = ownerId;
    }

    public Group(){

    }

    public void setOwner(User user){
        this.owner = user;
    }

    public int getOwnerId(){
        return this.ownerId;
    }

    public boolean isPrivate(){
        return this.isPrivate;
    }

    public User getOwner(){
        return this.owner;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return this.name + " [" + getMembers().size() + " members]";
    }

    public void addMember(User localUser) {
        this.getMembers().add(localUser);
    }
}
