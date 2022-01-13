package websockets;

public class ChatMessageEvent {

    private int chatId;
    private int senderId;

    private String body;
    private boolean isGroup;

    public int getChatId(){
        return this.chatId;
    }

    public int getSenderId(){
        return this.senderId;
    }

    public String getBody(){
        return this.body;
    }

    public boolean isGroup(){
        return this.isGroup;
    }

    public ChatMessageEvent(int chatId, int senderId, String body, boolean group){
        this.chatId = chatId;
        this.senderId = senderId;
        this.body = body;
        this.isGroup = group;
    }

    public ChatMessageEvent(){

    }


}
