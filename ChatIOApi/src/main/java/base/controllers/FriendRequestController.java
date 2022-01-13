package base.controllers;

import base.other.EmptyJsonResponse;
import base.repositories.ChatRepository;
import base.repositories.FriendRequestRepository;
import models.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendRequestController {

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    ChatRepository chatRepository;

    @PostMapping("/friend/request/add")
    public Object store(@RequestParam int senderId, int receiverId){
        if (senderId != 0 && receiverId != 0) {
            return friendRequestRepository.create(senderId, receiverId);
        }

        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/friend/request/accept")
    public Object accept(int recordId){
        if(recordId != 0){
            FriendRequest fr = friendRequestRepository.update(recordId, 2);
            if(fr != null){
                return chatRepository.create(fr.getSenderId(), fr.getReceiverId());
            }
        }

        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/friend/request/decline")
    public Object decline(int recordId){
        if(recordId != 0){
            return friendRequestRepository.update(recordId, 3);
        }

        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
    }
}
