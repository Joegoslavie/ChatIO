module ChatIOClient {
    requires javafx.graphics;
    requires shared;
    requires javafx.fxml;
    requires javafx.controls;
    requires javax.websocket.api;
    requires gson;
    requires slf4j.api;
    requires ChatIOCrypto;

    exports app;
    exports controllers;
    exports ws;
}