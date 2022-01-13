package base.controllers;

import base.other.EmptyJsonResponse;
import base.other.Utils;
import base.repositories.ChatRepository;
import base.repositories.FriendRequestRepository;
import base.repositories.GroupRepository;
import base.repositories.UserRepository;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    GroupRepository groupRepository;

    @GetMapping("/users/all")
    public List<User> all(@RequestParam int userId){

        return userRepository.getAll(userId);
    }

    @GetMapping("/users/id/get")
    public Object getById(@RequestParam int id){
        if(id != 0)
            return userRepository.getUserById(id);
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users/name/get")
    public Object getByUsername(@RequestParam String username){
        if(username != null)
            return userRepository.getUserByUsername(username);
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/account/register")
    public Object register(@RequestParam String username, String password) {
        if((username == null || username.length() < 3) || password == null || password.length() < 5)
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);

        if(userRepository.getUserByUsername(username) != null)
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.CONFLICT);

        return userRepository.create(username, Utils.hashPassword(password));
    }

    @PostMapping("/account/login")
    public Object signIn(@RequestParam String username, String password) {
        if((username == null || username.length() < 3) || (password == null || password.length() < 5))
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);

        if(Utils.verifyPassword(password, userRepository.getPasswordHash(username))){

            // Build the object with all its relationships
            User signedInUser = userRepository.getUserByUsername(username);

            signedInUser.setFriends(userRepository.getFriendsFromUser(signedInUser.getId()));
            signedInUser.setFriendRequests(friendRequestRepository.getRequestsByUserId(signedInUser.getId()));
            signedInUser.setChats(chatRepository.getChatsByUserId(signedInUser.getId()));
            signedInUser.setGroups(groupRepository.getGroupsByUserId(signedInUser.getId()));
            return signedInUser;
        }
        else
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.CONFLICT);
    }
}
