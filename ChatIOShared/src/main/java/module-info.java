module shared {
    exports websockets;
    exports enums;
    exports models;

    requires java.sql;
    requires javax.websocket.api;
    requires gson;
}