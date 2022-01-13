package controllers;

import mock.MainControllerMock;
import models.Conversation;
import models.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerMockTest {

    static MainControllerMock mainFrm;
    static boolean correctResult = true;

    @BeforeAll
    static void setup(){
        mainFrm = new MainControllerMock();
    }

    @Test
    void isConversationSelected() {
        Conversation convo = new Conversation(1, new Date());

        mainFrm.selectConversation(convo);
        boolean guiResult = mainFrm.isConversationSelected();

        assertEquals(correctResult, guiResult);
    }

    @Test
    void isMessageReceived() {
        Message msg = new Message();

        mainFrm.handleIncomingMessage(msg);
        boolean guiResult = mainFrm.isMessageReceived();

        assertEquals(correctResult, guiResult);
    }

    @Test
    void isMessageSent() {
        String msgToSend = "Hey there!";

        mainFrm.sendMessage(msgToSend);
        boolean guiResult = mainFrm.isMessageReceived();

        assertEquals(correctResult, guiResult);
    }

    @Test
    void isInputClear() {

        mainFrm.clearInput();
        boolean guiResult = mainFrm.isInputClear();

        assertEquals(correctResult, guiResult);
    }
}