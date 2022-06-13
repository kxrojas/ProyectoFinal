package co.edu.unbosque.services;

import co.edu.unbosque.dtos.NFT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnershipServices {

    private Connection conn;

    public OwnershipServices(Connection conn) {this.conn = conn;}

    public String buyNft(String username, String image) {
        PreparedStatement stmt = null;

        try {
            System.out.println("nft: "+ image);
            stmt = this.conn.prepareStatement("UPDATE ownership \n" +
                    "SET user_id = ?\n" +
                    "WHERE user_id = ?\n" +
                    "AND image = ?;");


            stmt.setString(1, username);
            stmt.setString(2, getOwnership(image));
            stmt.setString(3, image);

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
        return "Se compró exitosamente";
    }

    public String getOwnership(String image) {
        PreparedStatement stmt = null;
        String username = "";

        try {

            stmt = this.conn.prepareStatement("SELECT user_id\n" +
                    "FROM ownership\n" +
                    "WHERE image = ?;");

            stmt.setString(1, image);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            username = rs.getString("username");

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
        return username;
    }
}
