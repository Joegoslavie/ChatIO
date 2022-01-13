package uiInterface;

import models.Conversation;
import models.Message;

public interface IMainFrm {

    /**
     * Send a message to the selected conversation in the GUI
     * @param message message to send
     */
    void sendMessage(String message);


    /**
     * Clear the TextField used for sending a message
     */
    void clearInput();


    /**
     * Select a conversation and display it in the GUI
     * @param conversation the to select conversation
     */
    void selectConversation(Conversation conversation);


    /**
     * When the user has received a new message from the server
     * @param message the received message
     */
    void handleIncomingMessage(Message message);


    /**
     * Refresh the list of conversations of the user
     * Necessary if the user received a new conversation from the server
     */
    void handleIncomingConversation();
}
