package ua.kiev.prog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/")
public class MyController {

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String onLogin(Model model, @RequestParam String login, @RequestParam String password) throws SQLException {
        ResourceBundle res = ResourceBundle.getBundle("db");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    res.getString("db.url"), res.getString("db.user"), res.getString("db.password"));

            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS LogPas");
            statement.execute("CREATE TABLE LogPas " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "login VARCHAR (10) NOT NULL," +
                    "password VARCHAR (10) NOT NULL)");

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO LogPas(login, password) VALUES (?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT  * FROM LogPas");
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.print(metaData.getColumnName(i) + "\t\t");
            }
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "result";
    }

}