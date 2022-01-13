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
import models.Group;
import uiInterface.ISearchFrm;
import ws.ClientConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FindGroupController implements Initializable, ISearchFrm {

    private static final String GROUP_JOINED = "you joined this group!";
    private List<Group> groups;

    public TextField txtSearchQuery;
    public ListView lstSearchResults;
    public Button btnSearch;
    public Button btnJoin;
    public Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnection.getInstance().setGroupFrm(this);
        ClientConnection.getInstance().sendPacket(Header.REQUESTGROUPS, null);
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        doSearch(txtSearchQuery.getText());
    }

    private void doSearch(String query){
        List<Group> searchResults = groups.stream().filter(p -> p.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
        this.lstSearchResults.getItems().setAll(searchResults);
    }

    public void btnJoinClick(ActionEvent actionEvent) {
        if(lstSearchResults.getSelectionModel().getSelectedItem() != null){
            Group selectedGroup = (Group)lstSearchResults.getSelectionModel().getSelectedItem();

            ClientConnection.getInstance().sendPacket(Header.JOINGROUP, selectedGroup);
            groups.remove(selectedGroup);
            this.refresthList();

            lblStatus.setText(GROUP_JOINED);
        }
    }

    @Override
    public void loadData(Object data) {
        if(data != null){
            this.groups = (ArrayList<Group>)data;
            refresthList();
        }
    }

    @Override
    public void handleIncomingMessage(Object data) {
        throw new UnsupportedOperationException();
    }

    private void refresthList(){
        this.lstSearchResults.getItems().clear();
        this.lstSearchResults.getItems().setAll(groups);
    }

    public void txtSearchQueryKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            doSearch(txtSearchQuery.getText());
        }
    }
}
