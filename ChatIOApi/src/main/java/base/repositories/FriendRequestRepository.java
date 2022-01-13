package base.repositories;

import base.factories.FriendRequestFactory;
import models.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendRequestRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<FriendRequest> getRequestsByUserId(int userId){
        return jdbcTemplate.query("sp_GetFriendRequestsByUserId ?", new Object[] {userId}, new FriendRequestFactory());
    }

    public FriendRequest create(int senderId, int receiverId){
        return (FriendRequest)jdbcTemplate.queryForObject("sp_AddFriend ?, ?", new Object[] {senderId, receiverId}, new FriendRequestFactory());
    }

    public FriendRequest update(int id, int state){
        return (FriendRequest)jdbcTemplate.queryForObject("sp_UpdateFriendRequest ?, ?", new Object[] {id, state}, new FriendRequestFactory());
    }
}
