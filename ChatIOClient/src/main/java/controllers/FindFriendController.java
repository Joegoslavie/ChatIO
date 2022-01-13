package controllers;

import enums.Header;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.User;
import uiInterface.ISearchFrm;
import websockets.WebSocketMessage;
import ws.ClientConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FindFriendController implements Initializable, ISearchFrm {

    private static final String REQUEST_SENT = "request sent!";
    private List<User> allUsers;

    public TextField txtSearchQuery;
    public ListView lstSearchResults;
    public Button btnSearch;
    public Button btnAdd;
    public Label lblStatus;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnection.getInstance().setFriendsFrm(this);
        ClientConnection.getInstance().sendPacket(new WebSocketMessage(Header.REQUESTUSERS, null));
    }

    @Override
    public void loadData(Object data) {
        if(data != null){
            this.allUsers = (ArrayList<User>)data;
            refreshList();
        }
    }

    @Override
    public void handleIncomingMessage(Object data) {
        throw new UnsupportedOperationException();
    }

    private void refreshList(){
        this.lstSearchResults.getItems().clear();
        this.lstSearchResults.getItems().setAll(allUsers);
    }

    private void doSearch(String query){
        List<User> searchResults = allUsers.stream().filter(p -> p.getUsername().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
        this.lstSearchResults.getItems().setAll(searchResults);
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        doSearch(txtSearchQuery.getText());
    }

    public void btnAddClick(ActionEvent actionEvent) {
        if(lstSearchResults.getSelectionModel().getSelectedItem() != null){
            User selectedUser = (User)lstSearchResults.getSelectionModel().getSelectedItem();
            this.allUsers.remove(selectedUser);
            refreshList();

            ClientConnection.getInstance().sendPacket(Header.ADDUSER, selectedUser);
            lblStatus.setText(REQUEST_SENT);
        }
    }

    public void txtUsernameKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            doSearch(txtSearchQuery.getText());
        }
    }
}
