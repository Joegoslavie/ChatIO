package base.factories;

import models.FriendRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendRequestFactory implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        try {
            int id = resultSet.getInt("Id");
            String sender = resultSet.getString("Sender");
            int senderId = resultSet.getInt("SenderId");
            String receiver = resultSet.getString("Receiver");
            int receiverId = resultSet.getInt("ReceiverId");
            int status = resultSet.getInt("Status");

            return new FriendRequest(id, sender, senderId, receiver, receiverId, status);
        }catch(SQLException e){
            return null;
        }

    }
}
