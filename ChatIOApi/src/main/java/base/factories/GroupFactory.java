package base.factories;

import models.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupFactory implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            boolean isPriv = resultSet.getBoolean("Private");
            int ownerId = resultSet.getInt("OwnerId");
            Date created = resultSet.getDate("CreatedAt");

            return new Group(id, name, isPriv, ownerId, created);
        }catch(SQLException e){
            return null;
        }
    }
}
