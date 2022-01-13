package uiInterface;

public interface ILoginFrm {

    /**
     * Sign in a user at the server
     * @param username the username for the account
     * @param password the password for the account
     */
    void signIn(String username, String password);

    /**
     * Open the register page and display it
     */
    void openRegister();
}
