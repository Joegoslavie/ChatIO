package mock;

import uiInterface.IRegisterFrm;

public class RegisterControllerMock implements IRegisterFrm {

    private boolean registerComplete;
    private boolean usernameEmpty;
    private boolean passwordEmpty;
    private boolean loginOpened;

    public boolean isPasswordEmpty() {
        return passwordEmpty;
    }

    public boolean isLoginOpened() {
        return loginOpened;
    }

    public boolean isRegisterComplete() {
        return registerComplete;
    }

    public boolean isUsernameEmpty() {
        return usernameEmpty;
    }

    @Override
    public void register(String username, String password) {
        if(!(username.length() > 0))
            this.usernameEmpty = true;

        if(!(password.length() > 0))
            this.passwordEmpty = true;

        if(!this.usernameEmpty && !this.passwordEmpty)
            this.registerComplete = true;
    }

    @Override
    public void openLogin() {
        this.loginOpened = true;
    }

}
