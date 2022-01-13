package logic;

import models.Message;
import rest.MessageRest;
import websockets.ChatMessageEvent;

public class MessageService {

    private MessageRest restService;

    public MessageService(){
        this.restService = new MessageRest();
    }

    public Message store(ChatMessageEvent messageEvent){
        return restService.storeMessage(messageEvent.getSenderId(), messageEvent.getChatId(), messageEvent.getBody(), messageEvent.isGroup());
    }
}
