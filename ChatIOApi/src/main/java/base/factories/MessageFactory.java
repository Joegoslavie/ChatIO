package base.factories;

import models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageFactory implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            int id = resultSet.getInt("Id");
            int senderId = resultSet.getInt("SenderId");
            String name = resultSet.getString("Username");
            String body = resultSet.getString("Body");
            Date date = resultSet.getDate("CreatedAt");

            return new Message(id, senderId, name, body, date);
        }catch(SQLException e){
            return null;
        }
    }
}
