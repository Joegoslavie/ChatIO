package base.factories;

import models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) {

        try {
            int id = resultSet.getInt("Id");
            String username = resultSet.getString("Username");
            String avatar = resultSet.getString("Avatar");
            Date createdAt = resultSet.getDate("CreatedAt");

            return new User(id, username, avatar, createdAt);
        }catch(SQLException e){
            return null;
        }
    }
}
