package rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;
import rest.base.RestService;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRest extends RestService {

    private Gson gson = new Gson();

    public User signIn(String username, String password){

        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        String userData = postRequest("/account/login", params);
        User usr = gson.fromJson(userData, User.class);

        if(usr != null)
            return usr;
        return null;
    }

    public User register(String username, String password){

        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        String userData = postRequest("/account/register", params);
        User usr = gson.fromJson(userData, User.class);

        if(usr != null)
            return usr;
        return null;
    }

    public ArrayList<User> getAll(int localUserId) {
        String userData = getRequest("/users/all?userId=" + localUserId);

        if(userData != null){
            ArrayList<User> users = gson.fromJson(userData, new TypeToken<ArrayList<User>>(){}.getType());
            return users;
        }

        return null;
    }
}
