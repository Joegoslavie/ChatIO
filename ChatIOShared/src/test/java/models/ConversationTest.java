package models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConversationTest {

    static Conversation conversation;
    static Date usedDate;

    @BeforeAll
    static void setup(){
        usedDate = new Date();
        conversation = new Conversation(1, usedDate);

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        List<Message> msgs = new ArrayList<>();
        msgs.add(new Message());
        msgs.add(new Message());

        conversation.setMembers(users);
        conversation.setMessages(msgs);
    }


    @Test
    void getId() {
        int setId = 1;
        int id = conversation.getId();
        assertEquals(setId, id);
    }

    @Test
    void getCreatedAt() {
        Date setDate = usedDate;
        assertEquals(setDate, conversation.getCreatedAt());
    }

    @Test
    void getMembers() {
        int expectedCount = 2;
        List<User> members = conversation.getMembers();

        assertEquals(expectedCount, members.size());
    }

    @Test
    void getMessages() {
        int expectedCount = 3;
        List<Message> messages = conversation.getMessages();

        assertEquals(expectedCount, messages.size());
    }

    @Test
    void setMembers() {
        int expected = 1;

        Chat chat = new Chat(1, new Date());
        List<User> member = new ArrayList<>();
        member.add(new User());
        chat.setMembers(member);

        assertEquals(expected, chat.getMembers().size());
    }

    @Test
    void setMessages() {
        int expected = 1;

        Chat chat = new Chat(1, new Date());
        List<Message> messages = new ArrayList<>();
        messages.add(new Message());
        chat.setMessages(messages);

        assertEquals(expected, chat.getMessages().size());
    }

    @Test
    void addMessage() {
        int expected = 3;
        Message msg =  new Message();

        conversation.addMessage(msg);
        assertEquals(expected, conversation.getMessages().size());
    }

}