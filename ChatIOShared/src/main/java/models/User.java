package models;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String avatar;
    private Date createdAt;

    private List<User> friends;
    private List<FriendRequest> friendRequests;
    private List<Chat> chats;
    private List<Group> groups;

    private transient Session session;

    public int getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getAvatar(){
        return this.avatar;
    }

    public List<User> getFriends(){
        return this.friends;
    }

    public List<FriendRequest> getFriendRequests(){
        return this.friendRequests;
    }

    public List<Chat> getChats(){
        return this.chats;
    }

    public List<Group> getGroups(){
        return this.groups;
    }

    public Session getSession(){
        return this.session;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public void setFriends(List<User> users){
        this.friends = users;
    }

    public void setFriendRequests(List<FriendRequest> requests){
        this.friendRequests = requests;
    }

    public void setChats(List<Chat> chats){
        this.chats = chats;
    }

    public void setGroups(List<Group> groups){
        this.groups = groups;
    }

    public User(int id, String username, String avatar, Date date) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.createdAt = date;
    }

    public User(){

    }

    @Override
    public String toString(){
        return this.username;
    }
}
