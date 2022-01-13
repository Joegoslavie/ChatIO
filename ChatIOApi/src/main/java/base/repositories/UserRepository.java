package base.repositories;

import base.factories.PasswordFactory;
import base.factories.UserFactory;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll(int userId){
        return jdbcTemplate.query("sp_GetAllUsers ?", new Object[]{userId}, new UserFactory());
    }

    public User getUserById(int userId){
        User foundUser = null;
        try{
            foundUser = (User)jdbcTemplate.queryForObject("sp_GetUserById ?", new Object[] {userId}, new UserFactory());
        } catch(Exception e){
            log.error(e.getMessage());
        }

        return foundUser;
    }

    public User getUserByUsername(String username){
        User foundUser = null;
        try{
            foundUser = (User)jdbcTemplate.queryForObject("sp_GetUserByUsername ?", new Object[] {username}, new UserFactory());
        } catch(Exception e){
            log.error(e.getMessage());
        }

        return foundUser;
    }

    public List<User> getUsersByChat(int chatId){
        return jdbcTemplate.query("sp_GetUsersByChat ?", new Object[] {chatId}, new UserFactory());
    }

    public List<User> getUsersByGroup(int groupId){
        return jdbcTemplate.query("sp_GetUsersByGroup ?", new Object[] {groupId}, new UserFactory());
    }

    public List<User> getFriendsFromUser(int userId){
        return jdbcTemplate.query("sp_GetFriendsByUserId ?", new Object[] {userId}, new UserFactory());
    }

    public User create(String username, String password){
        return (User)jdbcTemplate.queryForObject("sp_CreateUser ?, ?", new Object[] {username, password}, new UserFactory());
    }

    public String getPasswordHash(String username){
        return (String)jdbcTemplate.queryForObject("sp_GetPassword ?", new Object[] {username}, new PasswordFactory());
    }
}
