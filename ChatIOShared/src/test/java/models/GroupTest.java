package models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    static Date date;
    static Group group;

    @BeforeAll
    static void setup(){
        date = new Date();
        group = new Group(1, "group", false, 1, date);

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        List<Message> msgs = new ArrayList<>();
        msgs.add(new Message());
        msgs.add(new Message());

        group.setMembers(users);
        group.setMessages(msgs);

        group.setOwner(new User(1, "hugo", "", new Date()));
    }

    @Test
    void getId() {
        int exp = 1;
        int id = group.getId();

        assertEquals(exp, id);
    }

    @Test
    void getCreatedAt() {
        Date usedDate = date;
        Date setDate = group.getCreatedAt();

        assertEquals(usedDate, setDate);
    }

    @Test
    void getMembers() {
        int expectedCount = 2;
        List<User> members = group.getMembers();

        assertEquals(expectedCount, members.size());
    }

    @Test
    void getMessages() {
        int expectedCount = 3;
        List<Message> messages = group.getMessages();

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

        group.addMessage(msg);
        assertEquals(expected, group.getMessages().size());
    }

    @Test
    void setOwner() {
        User owner = group.getOwner();

        assertNotNull(owner);
    }

    @Test
    void getOwnerId() {
        int exp = 1;
        int ownerId = group.getOwnerId();

        assertEquals(exp, ownerId);
    }

    @Test
    void isPrivate() {
        boolean exp = false;
        boolean ispriv = group.isPrivate();

        assertEquals(exp, ispriv);
    }

    @Test
    void getOwner() {
        User owner = group.getOwner();
        assertNotNull(owner);
    }

    @Test
    void getName() {
        String exp = "group";
        String name = group.getName();
        assertEquals(exp, name);
    }
}