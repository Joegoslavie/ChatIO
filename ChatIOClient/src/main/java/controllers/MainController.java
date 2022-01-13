package controllers;

import app.DisplayManager;
import enums.Display;
import enums.Header;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import models.*;
import uiInterface.IMainFrm;
import websockets.ChatMessageEvent;
import ws.ClientConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, IMainFrm {

    private static final int CLICK_COUNT = 2;

    public ListView lstActiveChats;
    public ListView lstActiveGroups;
    public TextArea txtMessage;
    public TextArea txtChatContent;
    public Button btnSendMessage;
    public Button btnClearMessage;
    public TitledPane chatPane;

    private User localUser;
    private Conversation selectedConversation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnection.getInstance().setMainFrm(this);
        this.localUser = ClientConnection.getInstance().getLocalUser();
        initFrm();
        setChatListListner();
        setGroupListListner();
    }

    private void setGroupListListner() {
        lstActiveGroups.setOnMouseClicked(click -> {
            if (click.getClickCount() == CLICK_COUNT) {
                Group selectedGroup = (Group) lstActiveGroups.getSelectionModel().getSelectedItem();
                selectConversation(selectedGroup);
            }
        });
    }

    private void setChatListListner() {
        lstActiveChats.setOnMouseClicked(click -> {
            if (click.getClickCount() == CLICK_COUNT) {
                Chat selectedChat = (Chat) lstActiveChats.getSelectionModel().getSelectedItem();
                selectConversation(selectedChat);
            }
        });
    }

    private void initFrm() {
        localUser.getChats().forEach(chat -> chat.setLocalUsername(localUser.getUsername()));
        lstActiveChats.getItems().setAll(localUser.getChats());
        lstActiveGroups.getItems().setAll(localUser.getGroups());
    }

    @Override
    public void sendMessage(String message) {
        if (message.equals(""))
            return;

        ClientConnection.getInstance().sendPacket(Header.MESSAGESENT, new ChatMessageEvent(selectedConversation.getId(), localUser.getId(), message, (selectedConversation.getMembers().size() > 2)));
        this.clearInput();
    }

    @Override
    public void clearInput() {
        this.txtMessage.setText("");
    }

    @Override
    public void selectConversation(Conversation conversation) {
        this.selectedConversation = conversation;
        txtChatContent.setText("");

        if(conversation.getMessages() != null){
            for (Message msg : conversation.getMessages()) {
                txtChatContent.appendText(msg.toString());
            }
        }

        if(conversation instanceof Chat)
            chatPane.setText("Chatting with " + ((Chat)conversation).toString());
        else
            chatPane.setText(((Group)conversation).getName());
    }

    @Override
    public void handleIncomingMessage(Message message) {
        Platform.runLater(() -> {
            if (message.getChatId() == selectedConversation.getId()) {
                txtChatContent.appendText(message.toString());
            }
        });
    }

    @Override
    public void handleIncomingConversation() {
        Platform.runLater(() -> initFrm());
    }

    public void btnSendMessageClick(ActionEvent actionEvent) {
        this.sendMessage(txtMessage.getText());
    }

    public void btnClearMessageClick(ActionEvent actionEvent) {
        this.clearInput();
    }

    public void menuAddFriend(ActionEvent actionEvent) {
        DisplayManager.getInstance().load(Display.FINDFRIEND);
    }

    public void menuCreateGroup(ActionEvent actionEvent) {
        DisplayManager.getInstance().load(Display.CREATEGROUP);
    }

    public void menuSearchGroup(ActionEvent actionEvent) {
        DisplayManager.getInstance().load(Display.FINDGROUP);
    }

    public void menuOpenFriendRequests(ActionEvent actionEvent) {
        DisplayManager.getInstance().load(Display.FRIENDREQUESTS);
    }
}
