package rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Group;
import rest.base.RestService;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupRest extends RestService {
    private Gson gson = new Gson();

    public boolean join(int groupId, int userId){

        HashMap<String, String> params = new HashMap<>();
        params.put("groupId", String.valueOf(groupId));
        params.put("userId", String.valueOf(userId));

        String groupData = postRequest("/group/join", params);
        return groupData != null;
    }

    public ArrayList<Group> getAll(int localUserId){
        String groupData = getRequest("/groups/all?userId=" + localUserId);

        if(groupData != null){
            return gson.fromJson(groupData, new TypeToken<ArrayList<Group>>(){}.getType());
        }

        return null;
    }
}
