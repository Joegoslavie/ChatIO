package logic;

import models.User;
import rest.UserRest;
import websockets.UserDataEvent;

public class AuthService {

    private UserRest userRestService;

    public AuthService(){
        this.userRestService = new UserRest();
    }

    public User signIn(UserDataEvent userData){
        return this.userRestService.signIn(userData.getUsername(), userData.getPassword());
    }

    public User register(UserDataEvent userData){
        return this.userRestService.register(userData.getUsername(), userData.getPassword());
    }

    public boolean signOut(String username) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
