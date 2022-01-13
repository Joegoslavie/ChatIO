package logic.collections;

import models.User;
import rest.UserRest;

import java.util.ArrayList;

public class UserCollection {

    private UserRest userRestService;

    public UserCollection(){
        userRestService = new UserRest();
    }

    public ArrayList<User> getAll(int localUserId){
        return this.userRestService.getAll(localUserId);
    }
}
