package rest;

import com.google.gson.Gson;
import models.Chat;
import rest.base.RestService;

import java.util.HashMap;

public class ChatRest extends RestService {

    private Gson gson = new Gson();

    public Chat startChat(int personA, int personB){

        HashMap<String, String> params = new HashMap<>();
        params.put("person_a", String.valueOf(personA));
        params.put("person_b", String.valueOf(personB));

        String chatData = postRequest("/chat/create", params);
        Chat createdChat = gson.fromJson(chatData, Chat.class);

        if(createdChat != null){
            return createdChat;
        }

        return null;
    }
}
