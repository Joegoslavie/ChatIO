package base.factories;

import models.Chat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatFactory implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            int id = resultSet.getInt("Id");
            Date date = resultSet.getDate("CreatedAt");

            return new Chat(id, date);
        }catch(SQLException e){
            return null;
        }
    }
}
