package models;

import enums.FriendStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendRequestTest {

    static FriendRequest friendRequest;

    @BeforeAll
    static void setup(){
        friendRequest = new FriendRequest(1, "Hugo", 1, "Jan", 2, 1);
    }

    @Test
    void getId() {
        int expected = 1;
        int id = friendRequest.getId();

        assertEquals(expected, id);
    }

    @Test
    void getSender() {
        String expected = "Hugo";
        String sender = friendRequest.getSender();

        assertEquals(expected, sender);
    }

    @Test
    void getSenderId() {
        int expected = 1;
        int id = friendRequest.getSenderId();

        assertEquals(expected, id);
    }

    @Test
    void getReceiver() {
        String expected = "Jan";
        String receiver = friendRequest.getReceiver();

        assertEquals(expected, receiver);
    }

    @Test
    void getReceiverId() {
        int expected = 2;
        int id = friendRequest.getReceiverId();

        assertEquals(expected, id);
    }

    @Test
    void getPendingStatus() {
        FriendStatus expected = FriendStatus.PENDING;
        FriendStatus status = friendRequest.getStatus();

        assertEquals(expected, status);
    }

    @Test
    void getAcceptedStatus(){
        FriendStatus expected = FriendStatus.ACCEPTED;
        FriendRequest testRequest = new FriendRequest(1, "Hugo", 1, "Jan", 2, 2);

        FriendStatus status = testRequest.getStatus();
        assertEquals(expected, status);
    }

    @Test
    void getDeclinedStatus(){
        FriendStatus expected = FriendStatus.DENIED;
        FriendRequest testRequest = new FriendRequest(1, "Hugo", 1, "Jan", 2, 3);

        FriendStatus status = testRequest.getStatus();
        assertEquals(expected, status);
    }

    @Test
    void getUndefinedStatus(){
        FriendStatus expected = FriendStatus.UNDEFINED;
        FriendRequest testRequest = new FriendRequest(1, "Hugo", 1, "Jan", 2, 4);

        FriendStatus status = testRequest.getStatus();
        assertEquals(expected, status);
    }
}