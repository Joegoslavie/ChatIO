package controllers;

import enums.Header;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.FriendRequest;
import uiInterface.ISearchFrm;
import ws.ClientConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FriendRequestController implements Initializable, ISearchFrm {

    private static final String REQ_ACCEPTED  = "request accepted!";
    private static final String REQ_DECLINED = "request declined!";

    private List<FriendRequest> friendRequests;

    public ListView lstFriendRequests;
    public Button btnAccept;
    public Button btnDecline;
    public Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnection.getInstance().setFriendReqFrame(this);
        friendRequests = ClientConnection.getInstance().getLocalUser().getFriendRequests();
        this.updateList();
    }


    public void btnAcceptClick(ActionEvent actionEvent) {
        if(lstFriendRequests.getSelectionModel().getSelectedItem() != null){
            FriendRequest friendReq = (FriendRequest)lstFriendRequests.getSelectionModel().getSelectedItem();
            friendRequests.remove(friendReq);

            ClientConnection.getInstance().sendPacket(Header.ACCEPTFRIEND, friendReq);
            lblStatus.setText(REQ_ACCEPTED);
            updateList();
        }
    }

    public void btnDeclineClick(ActionEvent actionEvent) {
        if(lstFriendRequests.getSelectionModel().getSelectedItem() != null){
            FriendRequest friendReq = (FriendRequest)lstFriendRequests.getSelectionModel().getSelectedItem();
            friendRequests.remove(friendReq);

            ClientConnection.getInstance().sendPacket(Header.DECLINEFRIEND, friendReq);
            lblStatus.setText(REQ_DECLINED);
            updateList();
        }
    }

    @Override
    public void loadData(Object data) {
        if(data != null){
            this.friendRequests = (ArrayList<FriendRequest>)data;
        }
    }

    @Override
    public void handleIncomingMessage(Object data) {
        if(data != null){
            FriendRequest fr = (FriendRequest)data;
            if(!ClientConnection.getInstance().getLocalUser().getUsername().equals(fr.getSender())){
                if(this.friendRequests == null)
                    this.friendRequests = new ArrayList<>();

                this.friendRequests.add(fr);
                updateList();
            }
        }
    }

    private void updateList(){
        lstFriendRequests.getItems().setAll(friendRequests);

        if(friendRequests == null || friendRequests.size() == 0){
            btnAccept.setDisable(true);
            btnDecline.setDisable(true);
        } else {
            btnAccept.setDisable(false);
            btnDecline.setDisable(false);
        }
    }
}
