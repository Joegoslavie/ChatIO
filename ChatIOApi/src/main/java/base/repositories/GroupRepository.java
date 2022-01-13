package base.repositories;

import base.factories.GroupFactory;
import models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    public List<Group> getAll(int userId){
        List<Group> groups = jdbcTemplate.query("sp_GetAllGroups ?", new Object[]{userId}, new GroupFactory());

        groups.forEach(group -> group.setOwner(userRepository.getUserById(group.getOwnerId())));
        groups.forEach(group -> group.setMessages(messageRepository.getMessagesByGroupId(group.getId())));
        groups.forEach(group -> group.setMembers(userRepository.getUsersByGroup(group.getId())));

        return groups;
    }

    public List<Group> getGroupsByUserId(int userId){
        List<Group> groups = jdbcTemplate.query("sp_GetGroupsByUserId ?", new Object[] {userId}, new GroupFactory());

        groups.forEach(group -> group.setOwner(userRepository.getUserById(group.getOwnerId())));
        groups.forEach(group -> group.setMessages(messageRepository.getMessagesByGroupId(group.getId())));
        groups.forEach(group -> group.setMembers(userRepository.getUsersByGroup(group.getId())));

        return groups;
    }

    public Group joinGroup(int groupId, int userId){
        return (Group)jdbcTemplate.queryForObject("sp_JoinGroup ?, ?", new Object[]{groupId, userId}, new GroupFactory());
    }

    public Group getGroupById(int groupId){
        throw new UnsupportedOperationException("Method not implemented!");
    }

    public Group getGroupByName(String name){
        throw new UnsupportedOperationException("Method not implemented!");
    }
}
