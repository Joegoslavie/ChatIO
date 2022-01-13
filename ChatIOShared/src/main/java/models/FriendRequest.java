package models;

import enums.FriendStatus;

import java.io.Serializable;

public class FriendRequest implements Serializable {

    private int id;

    private String sender;
    private int senderId;

    private String receiver;
    private int receiverId;

    private FriendStatus status;

    public int getId(){
        return this.id;
    }

    public String getSender(){
        return this.sender;
    }

    public int getSenderId(){
        return this.senderId;
    }

    public String getReceiver(){
        return this.receiver;
    }

    public int getReceiverId(){
        return this.receiverId;
    }

    public FriendStatus getStatus(){
        return this.status;
    }

    public FriendRequest(int id, String sender, int senderId, String receiver, int receiverId, int status){
        this.id = id;
        this.sender = sender;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.receiver = receiver;
        this.status = resolveStatus(status);
    }

    public FriendRequest(){

    }

    private FriendStatus resolveStatus(int status){
        switch(status){
            case 1:
                return FriendStatus.PENDING;
            case 2:
                return FriendStatus.ACCEPTED;
            case 3:
                return FriendStatus.DENIED;
        }

        // We got a mr hacker here
        return FriendStatus.UNDEFINED;
    }

    @Override
    public String toString(){
        return this.getSender();
    }
}
