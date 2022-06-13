package co.edu.unbosque.services;

import co.edu.unbosque.dtos.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserService {

    private Connection conn;

    public UserService(Connection conn) {
        this.conn = conn;
    }

    public List<User> listUsers() {

        Statement stmt = null;
        List<User> userList = new ArrayList<User>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM userapp";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String profileimage =rs.getString("profileimage");
                String role = rs.getString("role");

                userList.add(new User(username, role, password, profileimage));
            }
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return userList;
    }

    public User getUser(String username) {

        PreparedStatement stmt = null;
        User user = null;

        try {

            stmt = this.conn.prepareStatement("SELECT * FROM userapp WHERE user_id = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                user = new User(
                        rs.getString("user_id"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getString("profileimage")
                );
            }
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return user;
    }

    public User newUser(User user) {
        PreparedStatement stmt = null;

        if (user != null) {

            try {

                if (user.getRole().equals("Artista")) {
                    stmt = this.conn.prepareStatement("INSERT INTO UserApp (user_id, name, lastname, password, role, profileimage, description)\n" +
                            "VALUES (?,?,?,?,'Artista',?,'')");
                }

                else if (user.getRole().equals("Comprador")) {
                    stmt = this.conn.prepareStatement("INSERT INTO UserApp (user_id, name, lastname, password, role, profileimage, description)\n" +
                            "VALUES (?,?,?,?,'Comprador',?,'')");
                }
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getProfileImage());

                stmt.executeUpdate();
                stmt.close();
            } catch(SQLException se){
                se.printStackTrace();
            } finally{
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return user;
        }
        else {
            return null;
        }
    }
}