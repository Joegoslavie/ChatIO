package ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Display;
import enums.Header;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import security.AESCrypto;
import security.DHExchange;
import uiInterface.IFrm;
import uiInterface.IMainFrm;
import uiInterface.ISearchFrm;
import websockets.UserDataEvent;
import websockets.WebSocketMessage;

import javax.websocket.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class ClientConnection {

    private static final Logger log = LoggerFactory.getLogger(ClientConnection.class);

    private static String uri = "ws://localhost:8095/communicator/";
    private static ClientConnection connection;
    private static WebSocketContainer container;

    private Gson gson;

    private SecureRandom random;
    private DHExchange dhExchange;
    private AESCrypto crypto;
    private BigInteger clientKey;

    private Session localSession;
    private User localUser;

    private IFrm loginFrm = null;
    private IFrm registerFrm = null;
    private IMainFrm mainFrm = null;
    private ISearchFrm friendsFrm = null;
    private ISearchFrm groupFrm = null;
    private ISearchFrm friendReqFrm = null;

    public void setLoginFrm(IFrm frm){
        this.loginFrm = frm;
    }

    public void setRegisterFrm(IFrm frm){
        this.registerFrm = frm;
    }

    public void setMainFrm(IMainFrm frm){
        this.mainFrm = frm;
    }

    public void setFriendsFrm(ISearchFrm frm){
        this.friendsFrm = frm;
    }

    public void setGroupFrm(ISearchFrm frm){
        this.groupFrm = frm;
    }

    public void setFriendReqFrame(ISearchFrm frm){
        this.friendReqFrm = frm;
    }

    public User getLocalUser(){
        return this.localUser;
    }

    public ClientConnection(){
        log.info("app.Client connection class initialized");
        gson = new Gson();

        random = new SecureRandom();
        clientKey = BigInteger.probablePrime(128, random);
        dhExchange = new DHExchange(clientKey);
        crypto = new AESCrypto();

        log.info("Generated client key: " + clientKey);

        setup();
    }

    private void setup(){
        try {
            container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));
        } catch (IOException | URISyntaxException | DeploymentException exc) {
            log.error("Error occured: " + exc);
        }
    }

    public static ClientConnection getInstance() {
        if(connection == null)
            connection = new ClientConnection();
        return connection;
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("Websocket client -> session opened: " + session.getRequestURI());
        localSession = session;
    }

    @OnClose
    public void onClose(CloseReason reason) {
        log.info("Websocket client -> session closed: " + localSession.getRequestURI());
        log.info("Close reason: " + reason);
        localSession = null;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Websocket client -> received message " + message);

        WebSocketMessage packet = crypto.isValid() ? gson.fromJson(crypto.decrypt(message), WebSocketMessage.class) : gson.fromJson(message, WebSocketMessage.class);
        Header packetHeader = packet.getHeader();
        String content = crypto.isValid() ? crypto.decrypt(packet.getContent()): packet.getContent();

        switch(packetHeader){

            case INITCRYPTO:
                BigInteger pubKey = gson.fromJson(content, BigInteger.class);
                this.initCrypto(pubKey);
                break;

            case VALIDCRYPTO:
                this.crypto.setValid(true);
                System.out.println("There we go =D");
                break;

            case REGISTER:
                boolean isSucces = gson.fromJson(content, Boolean.class);
                if(isSucces)
                    registerFrm.takeStage(Display.LOGIN);
                else
                    registerFrm.displayMessage("Failed to create account!");
                break;

            case SIGNIN:
                User returnedUser = gson.fromJson(content, User.class);
                if(returnedUser != null){
                    this.localUser = returnedUser;
                    this.loginFrm.takeStage(Display.MAIN);
                }
                else
                    this.loginFrm.displayMessage("Failed to sign in!");
                break;

            case SIGNOUT:
                break;

            case REQUESTUSERS:
                List<User> allUsers = gson.fromJson(content, new TypeToken<ArrayList<User>>(){}.getType());
                this.friendsFrm.loadData(allUsers);
                break;

            case ADDUSER:
                FriendRequest receivedRequest = gson.fromJson(content, FriendRequest.class);
                this.friendReqFrm.handleIncomingMessage(receivedRequest);
                break;

            case REQUESTGROUPS:
                List<Group> allGroups = gson.fromJson(content, new TypeToken<ArrayList<Group>>(){}.getType());
                this.groupFrm.loadData(allGroups);
                break;

            case MESSAGERECEIVED:
                Message receivedMessage = gson.fromJson(content, Message.class);
                handleIncomingMessage(receivedMessage);
                break;

            case CHATINITIALIZED:
                Chat initializedChat = gson.fromJson(content, Chat.class);
                localUser.getChats().add(initializedChat);
                this.mainFrm.handleIncomingConversation();
                break;

            case GROUPINITIALIZED:
                Group initializedGroup = gson.fromJson(content, Group.class);
                localUser.getGroups().add(initializedGroup);
                this.mainFrm.handleIncomingConversation();
                break;

            case DECLINEFRIEND:
                FriendRequest req = gson.fromJson(content, FriendRequest.class);
                break;
        }
    }

    private void handleIncomingMessage(Message receivedMessage) {
        this.mainFrm.handleIncomingMessage(receivedMessage);
        boolean stored = false;

        for (Chat cht : localUser.getChats()) {
            if (cht.getId() == receivedMessage.getChatId()) {
                cht.addMessage(receivedMessage);
                stored = true;
            }
        }

        if(!stored){
            for(Group grp : localUser.getGroups()){
                if(grp.getId() == receivedMessage.getChatId()){
                    grp.addMessage(receivedMessage);
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable cause) {
        log.error("Websocket client -> connection error: " + cause);
    }

    public void signIn(String username, String password){
        WebSocketMessage packet = new WebSocketMessage(Header.SIGNIN, gson.toJson(new UserDataEvent(username, password)));
        this.sendPacket(packet);
    }

    public void sendInitCrypto(){
        WebSocketMessage packet = new WebSocketMessage(Header.INITCRYPTO, gson.toJson(this.dhExchange.getPublicKey()));
        this.sendPacket(packet);
    }

    public void initCrypto(BigInteger pubKey){
        this.dhExchange.setPublicKey(pubKey);
        this.crypto.setSecretKey(this.dhExchange.getSharedKey());

        String msg = this.crypto.encrypt("AliceMeetsBob");
        WebSocketMessage message = new WebSocketMessage(Header.VERIFYCRYPTO, gson.toJson(msg));

        sendPacket(message);
    }

    public void register(String username, String password){
        WebSocketMessage packet = new WebSocketMessage(Header.REGISTER, gson.toJson(new UserDataEvent(username, password)));
        this.sendPacket(packet);
    }

    public void sendPacket(WebSocketMessage message){
        try{
            String packet = crypto.isValid() ? crypto.encrypt(gson.toJson(message)) : gson.toJson(message);
            this.localSession.getBasicRemote().sendText(packet);
        }catch(Exception e){

        }
    }

    public void sendPacket(Header header, Object payload){
        try{
            WebSocketMessage message = new WebSocketMessage(header, gson.toJson(payload));
            String packet = crypto.isValid() ? crypto.encrypt(gson.toJson(message)) : gson.toJson(message);
            this.localSession.getBasicRemote().sendText(packet);
        }catch(Exception e){

        }
    }
}

