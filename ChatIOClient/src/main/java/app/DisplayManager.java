package app;

import enums.Display;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DisplayManager {

    private static final Logger log = LoggerFactory.getLogger(DisplayManager.class);
    private static DisplayManager displayManager;

    public static DisplayManager getInstance(){
        if(displayManager == null)
            displayManager = new DisplayManager();
        return displayManager;
    }

    public void load(Display display){
        switch(display){

            case LOGIN:
                this.load("/loginfrm.fxml", "Login");
                break;

            case REGISTER:
                this.load("/registerfrm.fxml", "Register");
                break;

            case MAIN:
                this.load("/mainfrm.fxml", "ChatIO");
                break;

            case FINDFRIEND:
                this.load("/frsearchfrm.fxml", "Search for friends");
                break;

            case FINDGROUP:
                this.load("/grsearchfrm.fxml", "Search for groups");
                break;

            case CREATEGROUP:
                this.load("/creategroupfrm.fmxl", "Create group");
                break;

            case FRIENDREQUESTS:
                this.load("/frrequestfrm.fxml", "Friend requests");
                break;

                default:
                    showAlert("Error!", "Display not found!", AlertType.ERROR);
        }
    }

    public void showAlert(String title, String message, AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void load(String fxmlFile, String title){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = null;

        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
