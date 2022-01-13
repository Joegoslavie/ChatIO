package base.controllers;

import base.repositories.MessageRepository;
import models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @PostMapping("/messages/chat/store")
    public Message store(@RequestParam int senderId, int destId, String body, boolean isGroup){
        return messageRepository.storeMessage(senderId, destId, body, isGroup);
    }
}
