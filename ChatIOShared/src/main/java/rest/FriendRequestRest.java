package rest;

import com.google.gson.Gson;
import enums.FriendStatus;
import models.Chat;
import models.FriendRequest;
import rest.base.RestService;

import java.util.HashMap;

public class FriendRequestRest extends RestService {

    private Gson gson = new Gson();

    public FriendRequest create(int senderId, int receiverId){

        HashMap<String, String> params = new HashMap<>();
        params.put("senderId", String.valueOf(senderId));
        params.put("receiverId", String.valueOf(receiverId));

        String responseData = postRequest("/friend/request/add", params);
        if(responseData != null)
        {
            FriendRequest req = gson.fromJson(responseData, FriendRequest.class);
            return req;
        }

        return null;
    }

    public Object update(int recordId, FriendStatus status){
        HashMap<String, String> params = new HashMap<>();
        params.put("recordId", String.valueOf(recordId));

        String url = "";
        if(status == FriendStatus.ACCEPTED)
            url = "/friend/request/accept";
        else
            url = "/friend/request/decline";

        String responseData = postRequest(url, params);

        if(responseData != null){
            if(status == FriendStatus.ACCEPTED)
                return gson.fromJson(responseData, Chat.class);
            else
                return gson.fromJson(responseData, FriendRequest.class);
        }

        return null;
    }

}
