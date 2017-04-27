package com.example;

/**
 * Created by Андрей on 13.02.2016.
 */


import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsersJDBCTemplate {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(String username, String password, String email) {
        String SQL = "insert into users (username, password, email) values (?, ?, ?)";

        jdbcTemplateObject.update( SQL, username, password, email);
        System.out.println("Created Record Name = " + username + " Password = " + "Email =" + email);
        return;
    }

    public Users getUsers(Integer id) {
        String SQL = "select * from users where id = ?";
        Users users = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id}, new UserMapper());
        return users;
    }

    public List<Users> listUsers() {
        String SQL = "select * from users";
        List <Users> users = jdbcTemplateObject.query(SQL,
                new UserMapper());
        return users;
    }

    public void delete(Integer id){
        String SQL = "delete from users where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
        return;
    }

    public void update(Integer id, String username, String password, String email){
        //В общем, выпутались через условия
        // username
        // password+
        // email +
        // username & password +
        // username & email +
        // email & password +
        // username & password & email +


        // username
        if ((!username.equals("null"))&&(password.equals("null"))&&(email.equals("null")))
        {String SQL = "UPDATE `mydbtest`.`users` SET `username`=? WHERE `id`=?";
        jdbcTemplateObject.update(SQL, username,id);
        System.out.println("Record with ID = " + id + "successfully updated ( new username = " + username + " )");
        return;}
        // password
        if ((username.equals("null"))&(!password.equals("null"))&(email.equals("null")))
        {String SQL = "UPDATE `mydbtest`.`users` SET `password`=? WHERE `id`=?;";
        jdbcTemplateObject.update(SQL, password, id);
            System.out.println("Record with ID = " + id + "successfully updated ( new password = " + password + " )");
        return;}

        // email
        if ((username.equals("null"))&(password.equals("null"))&(!email.equals("null")))
        {String SQL = "UPDATE `mydbtest`.`users` SET `email`=? WHERE `id`=?";
        jdbcTemplateObject.update(SQL, email, id);
        System.out.println("Record with ID = " + id + "successfully updated ( new email = " + email + " )");
        return;}
         // username & password
        if ((!username.equals("null"))&(!password.equals("null"))&(email.equals("null")))
        {String SQL = "UPDATE `mydbtest`.`users` SET `username` = ?, `password` = ?, WHERE `id`=?";
        jdbcTemplateObject.update(SQL, username, password, id);
        System.out.println("Record with ID = " + id + "successfully updated ( new username = " + username + " new password = " + password + " )");
        return;}
        // username & email
        if ((!username.equals("null"))&(password.equals("null"))&(!email.equals("null")))
        {String SQL = "UPDATE `mydbtest`.`users` SET `username` = ?, `email` = ? WHERE `id`=?";
        jdbcTemplateObject.update(SQL, username,  email, id);
            System.out.println("Record with ID = " + id + "successfully updated ( new username = " + username + ", new email = " + email + " )");
        return;}
        // password & email update
        if ((username.equals("null"))&(!password.equals("null"))&(!email.equals("null")))
        {String SQL = "UPDATE `mydbtest`.`users` SET `password` = ?, `email` = ? WHERE `id`=?";
        jdbcTemplateObject.update(SQL, password, email, id);
        System.out.println("Record with ID = " + id + "successfully updated ( new password = " + password + ", new email = " + email + " )");
        return;}
        // username & password & email
        if ((!username.equals("null"))&(!password.equals("null"))&(!email.equals("null")))
        {String SQL = "UPDATE `mydbtest`.`users` SET `username` = ?, `password` = ?, `email` = ? WHERE `id`=?";
            jdbcTemplateObject.update(SQL, username, password, email, id);
            System.out.println("Record with ID = " + id + "successfully updated ( new username = " + username + ", new password = " + password + ", new email = " + email + " )");
            return;}

    }


}




