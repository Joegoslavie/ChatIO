package controllers;

import mock.LoginControllerMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginControllerMockTest {

    @Test
    void testSignInEmptyUsername(){
        String username = "";
        String password = "secret";

        LoginControllerMock loginFrm = new LoginControllerMock();
        loginFrm.signIn(username, password);
        boolean guiResult = loginFrm.isUsernameEmpty();

        assertTrue(guiResult);
    }

    @Test
    void testSignInEmptyPassword(){
        String username = "hugo";
        String password = "";

        LoginControllerMock loginFrm = new LoginControllerMock();
        loginFrm.signIn(username, password);
        boolean guiResult = loginFrm.isPasswordEmpty();

        assertTrue(guiResult);
    }

    @Test
    void testSignInEmptyInput(){
        String username = "";
        String password = "";

        LoginControllerMock loginFrm = new LoginControllerMock();
        loginFrm.signIn(username, password);
        boolean usernameResult = loginFrm.isUsernameEmpty();
        boolean passwordResult = loginFrm.isPasswordEmpty();


        assertTrue(usernameResult);
        assertTrue(passwordResult);
    }

    @Test
    void testSignInSucces(){
        String username = "hugo";
        String password = "asd123";

        LoginControllerMock loginFrm = new LoginControllerMock();
        loginFrm.signIn(username, password);
        boolean guiResult = loginFrm.isSignInComplete();

        assertTrue(guiResult);
    }

    @Test
    void testOpenRegister(){
        LoginControllerMock loginFrm = new LoginControllerMock();
        loginFrm.openRegister();

        boolean guiResult = loginFrm.isRegisterOpened();
        assertTrue(guiResult);
    }

}
