package websockets;

public class UserDataEvent {

    private String username;
    private String password;

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public UserDataEvent(String username, String password){
        this.username = username;
        this.password = password;
    }
}
