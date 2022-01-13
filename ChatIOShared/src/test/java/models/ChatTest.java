package models;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    private static Chat chat;
    private static Date date;

    @BeforeAll
    static void setup(){
        date = new Date();
        chat = new Chat(1, date);

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        List<Message> msgs = new ArrayList<>();
        msgs.add(new Message());
        msgs.add(new Message());

        chat.setMembers(users);
        chat.setMessages(msgs);
    }

    @Test
    void getId() {
        int setId = 1;
        int id = chat.getId();
        assertEquals(setId, id);
    }

    @Test
    void getCreatedAt() {
        Date setDate = date;
        assertEquals(setDate, chat.getCreatedAt());
    }

    @Test
    void getMembers() {
        int expectedCount = 2;
        List<User> members = chat.getMembers();

        assertEquals(expectedCount, members.size());
    }

    @Test
    void getMessages() {
        int expectedCount = 3;
        List<Message> messages = chat.getMessages();

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

        chat.addMessage(msg);
        assertEquals(expected, chat.getMessages().size());
    }

    @Test
    void setLocalUsername() {
        String username = "pietje";
        chat.setLocalUsername(username);
    }
}