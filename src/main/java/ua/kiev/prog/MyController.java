package ua.kiev.prog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/")
public class MyController {

    static Connection connection;
    static boolean flag = true;

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView onLogin(ModelAndView model, @RequestParam String login, @RequestParam String password) throws SQLException {
        if (flag) {
            initDB();
            flag = false;
        }
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO LogPas(login, password) VALUES (?,?)");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();


        preparedStatement = connection.prepareStatement("SELECT  * FROM LogPas");
        ResultSet rs = preparedStatement.executeQuery();

        List<String> list = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();

        String s = "";
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            s += (metaData.getColumnName(i) + "\t\t");
        }
        list.add(s += "\n");
        while (rs.next()) {
            String l = "";
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                l += (rs.getString(i) + "\t\t");
            }
            list.add(l += "\n");
        }
        model.setViewName("result");
        model.addObject("list", list);
        return model;
    }

    private void initDB() {
        ResourceBundle res = ResourceBundle.getBundle("db");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    res.getString("db.url"), res.getString("db.user"), res.getString("db.password"));

            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS LogPas");
            statement.execute("CREATE TABLE LogPas " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "login VARCHAR (10) NOT NULL," +
                    "password VARCHAR (10) NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}