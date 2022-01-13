package base.controllers;

import base.repositories.GroupRepository;
import models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @GetMapping("/groups/all")
    public List<Group> all(@RequestParam int userId){
        return groupRepository.getAll(userId);
    }

    @PostMapping("/group/join")
    public Group join(@RequestParam int groupId, int userId){
        if(groupId != 0 && userId != 0)
        {
            return groupRepository.joinGroup(groupId, userId);
        }

        return null;
    }
}
