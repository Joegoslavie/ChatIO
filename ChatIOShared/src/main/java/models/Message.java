package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {

    private int id;
    private int chatId;
    private int senderId;
    private String senderName;
    private String body;
    private Date sentAt;

    public void setChatId(int id){
        this.chatId = id;
    }

    public Message(){

    }

    public Message(int id, int senderId, String sender, String body, Date sent){
        this.id = id;
        this.senderId = senderId;
        this.senderName = sender;
        this.body = body;
        this.sentAt = sent;
    }

    public int getId(){
        return this.id;
    }

    public String getSenderName(){
        return this.senderName;
    }

    public Date getSentAt() {
        return this.sentAt;
    }

    public String getBody(){
        return this.body;
    }

    public int getChatId(){
        return this.chatId;
    }

    @Override
    public String toString(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:ss");
        return "[" + this.senderName + "] [" + dateFormatter.format(this.sentAt) + "] --> " + this.body + "\n";
    }
}
