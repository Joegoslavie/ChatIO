package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import security.DHExchange;

import java.math.BigInteger;

public class Client extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/loginfrm.fxml"));
        stage.setTitle("ChatIO client");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }
}
