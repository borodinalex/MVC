package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.example.UsersJDBCTemplate;


@Controller
@EnableAutoConfiguration
public class DemoApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "\n \n Для вывода на экран пользователей поштучно - перейди по этой ссылке: \n " +
                "http://localhost:8080/users/{id}, где {id} - номер пользователя; \n " +
                " \n " +
                "Для вывода на экран пользователей массивом - перейди по этой ссылке \n:" +
                "http://localhost:8080/listusers/ наслаждайся;\n" +
                " \n " +
                "Для удаления пользователя - перейди по этой ссылке \n" +
                "http://localhost:8080/delete/{id}, где {id} - номер пользователя;" +
                " \n " +
                "Для обновления пользователя - перейди по этой ссылке \n" +
                "http://localhost:8080/update/{ID}?username={USERNAME}&password={PASSWORD}&email={EMAIL} \n" +
                "где {ID} - это ID пользователя, {USERNAME} - это имя пользхователя, \n " +
                "{PASSWORD} - это пароль пользователя, {EMAIL} - это почтовый ящик пользователя";
    }

    @RequestMapping("/users/{id}")
    @ResponseBody
    Users getUsers(@PathVariable int id) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Beans.xml");
        UsersJDBCTemplate usersJDBCTemplate =
                (UsersJDBCTemplate) context.getBean("usersJDBCTemplate");

        System.out.println("----Listing Record with ID = " + id + " -----");
        Users users = usersJDBCTemplate.getUsers(id);

        System.out.print("ID : " + users.getId());
        System.out.print(", Name : " + users.getUsername());
        System.out.println(", Password : " + users.getPassword());
        System.out.println(", Email : " + users.getEmail());

        // сюда вводим провеоку, не null ли пользователь, которого мы get?

        return users ;
    }

    @RequestMapping("/listusers/")
    @ResponseBody
    List <Users> listUsers() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Beans.xml");
        UsersJDBCTemplate usersJDBCTemplate =
                (UsersJDBCTemplate) context.getBean("usersJDBCTemplate");

        System.out.println("----Listing List of Users -----");
        List <Users> users = usersJDBCTemplate.listUsers();

        return users;
    }
    @RequestMapping("/delete/{id}")
    @ResponseBody
    String delete(@PathVariable int id) { // Вначале сделал его void,
        // но потом передумал. Отчитываться об удалении - круто
        // для того, чтобы отчитаться, о том, что действительно что-тоудалил -
        // сначала запрашиваем, было ли что-то, что удаляем?

        ApplicationContext context =
                new ClassPathXmlApplicationContext("Beans.xml");
        UsersJDBCTemplate usersJDBCTemplate =
                (UsersJDBCTemplate) context.getBean("usersJDBCTemplate");

        System.out.println("----Deleting Record with ID = " + id + " -----");
        usersJDBCTemplate.delete(id);
        // Сюда добавить триггер - если только что удалил кого-нибудь - пишем следующую строчку:
    return "User ID " + id + " successfully deleted! ";
        // А если хотел удалить, но не кого было удалять - так и сказать - мол, некого удалять!!!
    }

    // http://localhost:8080/update/4?username=Vasiliy&password=Qwerty&email=Email@Email.ru
   @RequestMapping("/update/{id}")
   @ResponseBody
   // Подумать над тем, как оставлять дефолтными значения, которые я не ввел
   // Придмумал и реализовал оставление дефолтных значений дефолтными, сделал это через добавление условий в UsersJDBCTemlate

   String update (@PathVariable int id, @RequestParam(value="username", defaultValue="null" ) String username,
                  @RequestParam(value="password", defaultValue = "null") String password,
                  @RequestParam(value = "email", defaultValue = "null") String email) {
       ApplicationContext context =
               new ClassPathXmlApplicationContext("Beans.xml");
       UsersJDBCTemplate usersJDBCTemplate =
               (UsersJDBCTemplate) context.getBean("usersJDBCTemplate");

       System.out.println("----Updating Record with ID = " + id + " -----");
       usersJDBCTemplate.update(id, username, password, email); // Удалять эту строку - не надо. Метод работать не будет

       // Красиво расписать, какие значения новые появились в базе данных у юзера
       // Сделать проверку на то, что апдейт данных действительно имел место быть.
       return "User ID " + id + "successfully updated!";
   }


    // http://localhost:8080/create/?username=Vasiliy&password=Qwerty&email=Email@Email.ru
    @RequestMapping("/create/")
    @ResponseBody
    String create (@RequestParam(value="username", defaultValue="root") String username,
                   @RequestParam(value="password", defaultValue = "root") String password,
                   @RequestParam(value = "email", defaultValue = "email@email.com") String email) {
       ApplicationContext context =
               new ClassPathXmlApplicationContext("Beans.xml");
       UsersJDBCTemplate usersJDBCTemplate =
               (UsersJDBCTemplate) context.getBean("usersJDBCTemplate");

       System.out.println("----Creating Record -----");
       usersJDBCTemplate.create( username, password, email);
       // Красиво расписать, какие значения новые появились в базе данных у юзера
       return "User " + username + "successfully created!";
   }


}
