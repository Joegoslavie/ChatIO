package mock;

import uiInterface.ILoginFrm;

public class LoginControllerMock implements ILoginFrm {

    private boolean signInComplete;
    private boolean usernameEmpty;
    private boolean passwordEmpty;
    private boolean registerOpened;

    public boolean isPasswordEmpty() {
        return passwordEmpty;
    }

    public boolean isRegisterOpened() {
        return registerOpened;
    }

    public boolean isSignInComplete() {
        return signInComplete;
    }

    public boolean isUsernameEmpty() {
        return usernameEmpty;
    }

    @Override
    public void signIn(String username, String password) {
        if(!(username.length() > 0))
            this.usernameEmpty = true;

        if(!(password.length() > 0))
            this.passwordEmpty = true;

        if(!this.usernameEmpty && !this.passwordEmpty)
            this.signInComplete = true;
    }

    @Override
    public void openRegister() {
        this.registerOpened = true;
    }
}
