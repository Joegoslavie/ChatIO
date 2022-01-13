package websockets;

import enums.Header;

public class WebSocketMessage {
    private Header header;
    private String content;

    public Header getHeader(){
        return this.header;
    }

    public String getContent(){
        return this.content;
    }

    public WebSocketMessage(Header header, String content){
        this.header = header;
        this.content = content;
    }

    public WebSocketMessage(){

    }
}
