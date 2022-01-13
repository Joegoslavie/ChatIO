package ws;

import com.google.gson.Gson;
import enums.FriendStatus;
import enums.Header;
import interfaces.IConnection;
import logic.AuthService;
import logic.FriendRequestService;
import logic.GroupService;
import logic.MessageService;
import logic.collections.GroupCollection;
import logic.collections.UserCollection;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import security.AESCrypto;
import security.DHExchange;
import websockets.ChatMessageEvent;
import websockets.UserDataEvent;
import websockets.WebSocketMessage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/communicator/")
public class ServerConnection implements IConnection {

    private static final Logger log = LoggerFactory.getLogger(ServerConnection.class);

    private Gson gson = new Gson();

    private SecureRandom random;
    private DHExchange dhExchange;
    private AESCrypto crypto;
    private BigInteger serverKey;

    private static AuthService authService = new AuthService();
    private static FriendRequestService friendService = new FriendRequestService();
    private static MessageService messageService = new MessageService();
    private static GroupService groupService = new GroupService();

    private static ArrayList<User> onlineUsers = new ArrayList<>();

    private Session localSession;
    private User localUser;

    public ServerConnection(){
        random = new SecureRandom();
        serverKey = BigInteger.probablePrime(128, random);
        dhExchange = new DHExchange(serverKey);
        crypto = new AESCrypto();

        log.info("Generated server key: " + serverKey);
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("Websocket server -> client connected: " + session.getRequestURI());
        localSession = session;
    }

    @OnClose
    public void onClose(CloseReason reason) {
        log.info("Websocket server -> client closed: " + localSession.getRequestURI());
        log.info("Close reason: " + reason);

        localSession = null;
        localUser = null;
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("Websocket server -> received message " + message);

        WebSocketMessage packet = crypto.isValid() ? gson.fromJson(crypto.decrypt(message), WebSocketMessage.class) : gson.fromJson(message, WebSocketMessage.class);
        Header packetHeader = packet.getHeader();
        String content = packet.getContent();

        log.info("Websocket server -> received header " + packetHeader.toString());

        switch(packetHeader) {

            case INITCRYPTO:
                BigInteger pubKey = gson.fromJson(content, BigInteger.class);
                this.initCrypto(pubKey);
                break;

            case VERIFYCRYPTO:
                String verifyText = gson.fromJson(content, String.class);
                this.verifyCrypto(verifyText);
                break;

            case REGISTER:
                boolean success = false;
                if(authService.register(gson.fromJson(content, UserDataEvent.class)) != null)
                    success = true;
                sendPacket(new WebSocketMessage(Header.REGISTER, gson.toJson(success)));
                break;

            case SIGNIN:
                User signUser = authService.signIn(gson.fromJson(content, UserDataEvent.class));
                if(signUser != null)
                    setupChats(signUser);

                sendPacket(new WebSocketMessage(Header.SIGNIN, gson.toJson(signUser)));
                break;

            case SIGNOUT:
                break;

            case REQUESTUSERS:
                List<User> allUsers = new UserCollection().getAll(localUser.getId());
                sendPacket(new WebSocketMessage(Header.REQUESTUSERS, gson.toJson(allUsers)));
                break;

            case ADDUSER:
                User receivedUser = gson.fromJson(content, User.class);
                FriendRequest createdRequest = friendService.addFriend(localUser, receivedUser);

                sendPacket(new WebSocketMessage(Header.ADDUSER, gson.toJson(createdRequest)));
                sendPacket(new WebSocketMessage(Header.ADDUSER, gson.toJson(createdRequest)), getActiveSessionOfUser(receivedUser.getId()));
                break;

            case ACCEPTFRIEND:
                FriendRequest acceptreq = gson.fromJson(content, FriendRequest.class);
                Chat newChat = (Chat)friendService.update(acceptreq, FriendStatus.ACCEPTED);
                handleNewInitializedChat(newChat);
                break;

            case DECLINEFRIEND:
                FriendRequest declinereq = gson.fromJson(content, FriendRequest.class);
                FriendRequest updatedreq = (FriendRequest)friendService.update(declinereq, FriendStatus.DENIED);
                sendPacket(new WebSocketMessage(Header.DECLINEFRIEND, gson.toJson(updatedreq)));
                break;

            case REQUESTGROUPS:
                List<Group> allGroups = new GroupCollection().getAll(localUser.getId());
                sendPacket(new WebSocketMessage(Header.REQUESTGROUPS, gson.toJson(allGroups)));
                break;

            case JOINGROUP:
                Group selectedGroup = gson.fromJson(content, Group.class);
                if(groupService.join(selectedGroup, localUser)){
                    selectedGroup.addMember(localUser);
                    broadcastMessage(selectedGroup, Header.JOINGROUP, gson.toJson(localUser));
                    sendPacket(new WebSocketMessage(Header.GROUPINITIALIZED, gson.toJson(selectedGroup)));
                } else{
                    sendPacket(new WebSocketMessage(Header.JOINGROUP, null));
                }
                break;

            case MESSAGESENT:
                ChatMessageEvent chatMessage = gson.fromJson(content, ChatMessageEvent.class);
                handleIncomingChatMessage(chatMessage);
                break;
        }
    }

    private void verifyCrypto(String verifyText) throws IOException {
        String plaintext = "AliceMeetsBob";
        WebSocketMessage message = gson.fromJson(this.crypto.decrypt(verifyText), WebSocketMessage.class);
        String cipher = this.crypto.decrypt(message.getContent());

        if(plaintext.equals(cipher))
        {
            sendPacket(new WebSocketMessage(Header.VALIDCRYPTO, null));
            this.crypto.setValid(true);
        }
        else
            localSession.close();
    }

    private void initCrypto(BigInteger pubKey) {
        this.dhExchange.setPublicKey(pubKey); // Set public key of client to setup shared key
        this.crypto.setSecretKey(this.dhExchange.getSharedKey()); // Initialize AES with shared key

        log.info("Shared key: " + this.dhExchange.getSharedKey());
        sendPacket(new WebSocketMessage(Header.INITCRYPTO, gson.toJson(dhExchange.getPublicKey())));
    }

    private void handleNewInitializedChat(Chat newChat) {
        this.localUser.getChats().add(newChat);
        broadcastMessage(newChat, Header.CHATINITIALIZED, gson.toJson(newChat));
    }

    private void handleIncomingChatMessage(ChatMessageEvent chatMessage) {
        Message sentMessage = messageService.store(chatMessage);
        sentMessage.setChatId(chatMessage.getChatId());

        Conversation selectedConversation = null;

        if(chatMessage.isGroup())
            selectedConversation = getGroupById(chatMessage.getChatId());
        else
            selectedConversation = getChatById(chatMessage.getChatId());

        if(selectedConversation != null){
            broadcastMessage(selectedConversation, Header.MESSAGERECEIVED, gson.toJson(sentMessage));
        }
    }

    private Conversation getGroupById(int chatId) {
        for(Group grp : localUser.getGroups()){
            if(grp.getId() == chatId){
                return grp;
            }
        }
        return null;
    }

    private Chat getChatById(int chatId) {

        for(Chat cht : localUser.getChats()){
            if(cht.getId() == chatId){
                return cht;
            }
        }
        return null;
    }

    private Session getActiveSessionOfUser(int userId){
        for(User u : onlineUsers){
            if(u.getId() == userId){
                return u.getSession();
            }
        }

        return null;
    }

    private void setupChats(User signUser) {
        this.localUser = signUser;
        this.localUser.setSession(localSession);
        onlineUsers.add(localUser);
    }

    @OnError
    public void onError(Session session, Throwable cause) {
        log.error("Websocket server -> connection error: " + cause);
    }

    private void broadcastMessage(Conversation convo, Header header, String payload){
        for(User usr : convo.getMembers()) {
            Session session = getActiveSessionOfUser(usr.getId());
            if (session != null) {
                sendPacket(new WebSocketMessage(header, payload), session);
            }
        }
    }

    @Override
    public void sendPacket(WebSocketMessage message) {
        String packet = crypto.isValid() ? crypto.encrypt(gson.toJson(message)) : gson.toJson(message);
        this.localSession.getAsyncRemote().sendText(packet);
    }

    @Override
    public void sendPacket(WebSocketMessage message, Session session) {
        String msg = crypto.isValid() ? crypto.encrypt(gson.toJson(message)) : gson.toJson(message);
        session.getAsyncRemote().sendText(msg);
    }
}
