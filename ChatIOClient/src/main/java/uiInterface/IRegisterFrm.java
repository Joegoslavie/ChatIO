package uiInterface;

public interface IRegisterFrm {

    /**
     * Register a new user at the server
     * @param username the username for the new account
     * @param password the password for the new account
     */
    void register(String username, String password);

    /**
     * Open the login page and display it
     */
    void openLogin();
}
