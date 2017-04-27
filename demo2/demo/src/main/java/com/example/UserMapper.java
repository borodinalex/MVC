package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<Users> {
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        Users users = new Users();
        users.setId(rs.getInt("id"));
        users.setUsername(rs.getString("username"));
        users.setPassword(rs.getString("password"));
        users.setEmail(rs.getString("email"));

        return users;
    }
}