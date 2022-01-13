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
import uiInterface.ILoginFrm;
import ws.ClientConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, ILoginFrm, IFrm {

    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnSignIn;
    public Button btnSignUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnection.getInstance().setLoginFrm(this);
        ClientConnection.getInstance().sendInitCrypto();
    }

    public void btnSignInClick(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(!username.equals("") && !password.equals(""))
            this.signIn(username, password);
        else
            DisplayManager.getInstance().showAlert("Something went wrong!", "Please fill in all fields", Alert.AlertType.ERROR);
    }

    public void btnSignUpClick(ActionEvent actionEvent) {
        this.openRegister();
    }

    @Override
    public void signIn(String username, String password) {
        ClientConnection.getInstance().signIn(username, password);
    }

    @Override
    public void openRegister() {
        DisplayManager.getInstance().load(Display.REGISTER);
        this.close();
    }

    @Override
    public void takeStage(Display display) {
        Platform.runLater(() -> {
            DisplayManager.getInstance().load(Display.MAIN);
            close();
        });
    }

    @Override
    public void displayMessage(String message) {
        throw new UnsupportedOperationException();
    }

    private void close() {
        Stage currentStage = (Stage)btnSignUp.getScene().getWindow();
        currentStage.close();
    }
}
