package controllers;

import enums.Display;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import uiInterface.IFrm;
import websockets.CreateGroupEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateGroupController implements Initializable, IFrm {

    public TextField txtGroupName;
    public RadioButton chkPrivate;
    public ListView lstUsers;
    public Button btnCreateGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void takeStage(Display display) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void displayMessage(String message) {
        throw new UnsupportedOperationException();
    }

    public void btnCreateGroupClick(ActionEvent actionEvent) {
        throw new UnsupportedOperationException();
    }
}
