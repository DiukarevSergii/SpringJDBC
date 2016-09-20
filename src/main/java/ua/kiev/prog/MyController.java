package ua.kiev.prog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
@RequestMapping("/")
public class MyController {

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String onLogin(Model model, @RequestParam String login, @RequestParam String password) throws SQLException {
//        String log = login;
//        String pas = password;
        initDB();

        return "result";
    }

    private static void initDB() {
        String url = "jdbc:mysql://localhost:3306/TestDB";
        String user = "root";
        String password = 666999 + "";
        System.out.println(url + " " + user + " " + password);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS LogPas");
            statement.execute("CREATE TABLE LogPas " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "login VARCHAR (10) NOT NULL," +
                    "password VARCHAR (10) NOT NULL)");

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO LogPas(login, password) VALUES (?,?)");
            preparedStatement.setString(1, "sdfsf");
            preparedStatement.setString(2, "sfsfsd");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}