package mock;

import models.Conversation;
import models.Message;
import uiInterface.IMainFrm;

public class MainControllerMock implements IMainFrm {

    private boolean conversationSelected;
    private boolean messageReceived;
    private boolean messageSent;
    private boolean inputClear;

    public boolean isConversationSelected(){
        return this.conversationSelected;
    }

    public boolean isMessageReceived() {
        return messageReceived;
    }

    public boolean isMessageSent() {
        return messageSent;
    }

    public boolean isInputClear() {
        return inputClear;
    }

    @Override
    public void sendMessage(String message) {
        this.messageSent = true;
    }

    @Override
    public void clearInput() {
        this.inputClear = true;
    }

    @Override
    public void selectConversation(Conversation conversation) {
        this.conversationSelected = true;
    }

    @Override
    public void handleIncomingMessage(Message message) {
        this.messageReceived = true;
    }

    @Override
    public void handleIncomingConversation() {

    }
}
