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

    public List<UserApp> listUsers() {

        Statement stmt = null;
        List<UserApp> userList = new ArrayList<UserApp>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM userapp";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String profileimage =rs.getString("profileimage");
                String role = rs.getString("role");

                userList.add(new UserApp(username, role, password));
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

    public UserApp getUser(String username) {

        PreparedStatement stmt = null;
        UserApp user = null;

        try {

            stmt = this.conn.prepareStatement("SELECT * FROM userapp WHERE user_id = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                user = new UserApp(
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("password")
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

    public UserApp newUserApp(UserApp user) {
        System.out.println(user.toString());
        // Object for handling SQL statement
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;

        // Data structure to map results from database
        if (user != null) {

            try {

                stmt2= this.conn.prepareStatement("INSERT INTO Usuario (password, username, role) VALUES (?, ?, ?)");
                stmt2.setString(1, user.getPassword());
                stmt2.setString(2, user.getUsername());
                stmt2.setString(3, user.getRole());
                stmt2.executeUpdate();
                stmt2.close();
                if (user.getRole().equals("Artista")) {
                    System.out.println("Es artista");
                    stmt = this.conn.prepareStatement("INSERT INTO Artista (Password,Username)\n" +
                            "VALUES (?,?)");
                    stmt.setString(1, user.getPassword());
                    stmt.setString(2, user.getUsername());
                    stmt.executeUpdate();
                    stmt.close();
                }

                else if (user.getRole().equals("Comprador")) {
                    System.out.println("Es comprador");
                    stmt1 = this.conn.prepareStatement("INSERT INTO Comprador(username, Password,fcoins)\n" +
                            "VALUES (?,?,?)");
                    stmt1.setString(1, user.getUsername());
                    stmt1.setString(2, user.getPassword());
                    stmt1.setInt(3, (0));
                    stmt1.executeUpdate();
                    stmt1.close();
                }

            } catch(SQLException se){
                se.printStackTrace(); // Handling errors from database
            } finally{
                // Cleaning-up environment
                try {
                    if (stmt != null) stmt.close();
                    if (stmt1 != null) stmt1.close();
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