package controllers;

import mock.LoginControllerMock;
import mock.RegisterControllerMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterControllerMockTest {

    @Test
    void testRegisterEmptyUsername(){
        String username = "";
        String password = "secret";

        RegisterControllerMock registeFrm = new RegisterControllerMock();
        registeFrm.register(username, password);
        boolean guiResult = registeFrm.isUsernameEmpty();

        assertTrue(guiResult);
    }

    @Test
    void testRegisterEmptyPassword(){
        String username = "hugo";
        String password = "";

        RegisterControllerMock registeFrm = new RegisterControllerMock();
        registeFrm.register(username, password);
        boolean guiResult = registeFrm.isPasswordEmpty();

        assertTrue(guiResult);
    }

    @Test
    void testRegisterEmptyInput(){
        String username = "";
        String password = "";

        RegisterControllerMock registeFrm = new RegisterControllerMock();
        registeFrm.register(username, password);
        boolean usernameResult = registeFrm.isUsernameEmpty();
        boolean passwordResult = registeFrm.isPasswordEmpty();


        assertTrue(usernameResult);
        assertTrue(passwordResult);
    }

    @Test
    void testRegisterSucces(){
        String username = "hugo";
        String password = "asd123";

        RegisterControllerMock registeFrm = new RegisterControllerMock();
        registeFrm.register(username, password);
        boolean guiResult = registeFrm.isRegisterComplete();

        assertTrue(guiResult);
    }

    @Test
    void testOpenRegister(){
        RegisterControllerMock registeFrm = new RegisterControllerMock();
        registeFrm.openLogin();

        boolean guiResult = registeFrm.isLoginOpened();
        assertTrue(guiResult);
    }
}
