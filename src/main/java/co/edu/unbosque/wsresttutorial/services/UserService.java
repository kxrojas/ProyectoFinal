package co.edu.unbosque.wsresttutorial.services;

import co.edu.unbosque.wsresttutorial.dtos.User;
import com.opencsv.bean.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private Connection conn;

    public UserService(Connection conn){
        this.conn = conn;
    }

    public List<User> listUsers() {

        Statement statement = null;
        List<User> usersList = new ArrayList<User>();

        try {
            statement = conn.createStatement();
            String sql = "SELECT * FROM userapp";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String username = resultSet.getString("userId");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                usersList.add(new User(username, password, role));
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usersList;
    }

    public List<User> getUsers() throws IOException {

        List<User> users;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("users.csv")) {

            HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(User.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(br)
                        .withType(User.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                users = csvToBean.parse();
            }
        }

        return users;
    }

    public User newUser(User user) {
        PreparedStatement statement = null;

        if (user != null) {
            try {

                if (user.getRole().equals("Artista")) {
                    statement = this.conn.prepareStatement("INSERT INTO UserApp (userId, password, role) " +
                            "VALUES (?,?,'Artista')");
                }
                else if (user.getRole().equals("Comprador")) {
                    statement = this.conn.prepareStatement("INSERT INTO UserApp (userId, password, role) " +
                            "VALUES (?,?,'Comprador')");
                }
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.executeUpdate();
                statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return user;
        }
        else {
            return null;
        }
    }

    public User getUser(String username) {
        PreparedStatement statement = null;
        User user = null;

        try {
            statement = this.conn.prepareStatement("SELECT * FROM userApp WHERE userId = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("role"));
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null ){
                    statement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}