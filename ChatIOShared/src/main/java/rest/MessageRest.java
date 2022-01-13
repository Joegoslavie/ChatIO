package rest;

import com.google.gson.Gson;
import models.Message;
import rest.base.RestService;

import java.util.HashMap;

public class MessageRest extends RestService {

    private Gson gson = new Gson();

    public Message storeMessage(int senderId, int destId, String body, boolean isGroup){

        HashMap<String, String> params = new HashMap<>();
        params.put("senderId", String.valueOf(senderId));
        params.put("destId", String.valueOf(destId));
        params.put("body", body);
        params.put("isGroup", String.valueOf(isGroup));

        String messageData = postRequest("/messages/chat/store", params);
        if(messageData != null){
            Message message = gson.fromJson(messageData, Message.class);
            return message;
        }

        return null;
    }
}
