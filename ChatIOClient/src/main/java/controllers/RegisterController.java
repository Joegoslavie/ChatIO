package controllers;

import app.DisplayManager;
import enums.Display;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uiInterface.IFrm;
import uiInterface.IRegisterFrm;
import ws.ClientConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable, IRegisterFrm, IFrm {

    public TextField txtUsername;
    public PasswordField txtPassword;
    public PasswordField txtPasswordVerify;
    public Button btnSignUp;
    public Button btnSignIn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnection.getInstance().setRegisterFrm(this);
    }

    public void btnSignUpClick(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String passwordVerify = txtPasswordVerify.getText();

        if(!username.equals("") && !password.equals("") && !passwordVerify.equals("")){
            if(password.equals(passwordVerify)) {
                this.register(username, password);
                return;
            }
        }

        DisplayManager.getInstance().showAlert("Something went wrong", "Not all the fields are correctly filled in", Alert.AlertType.ERROR);
    }

    public void btnSignInClick(ActionEvent actionEvent) {
        this.openLogin();
    }

    @Override
    public void register(String username, String password) {
        ClientConnection.getInstance().register(username, password);
    }

    @Override
    public void openLogin() {
        DisplayManager.getInstance().load(Display.LOGIN);
        close();
    }

    @Override
    public void takeStage(Display display) {
        Platform.runLater(() -> {
            DisplayManager.getInstance().load(Display.LOGIN);
            close();
        });
    }

    @Override
    public void displayMessage(String message) {

    }

    private void close(){
        Stage currentStage = (Stage)btnSignIn.getScene().getWindow();
        currentStage.close();
    }
}
