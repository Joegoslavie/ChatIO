package interfaces;

import websockets.WebSocketMessage;

import javax.websocket.Session;

public interface IConnection {

    /**
     * Send a websocket message to the server
     * @param message the websocket object
     */
    void sendPacket(WebSocketMessage message);

    /**
     * Send a websocket message to the specified session
     * @param message the websocket object
     * @param session the session
     */
    void sendPacket(WebSocketMessage message, Session session);

}
