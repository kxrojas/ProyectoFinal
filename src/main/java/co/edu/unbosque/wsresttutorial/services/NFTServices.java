package co.edu.unbosque.services;

import co.edu.unbosque.dtos.NFT;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NFTServices {

    private Connection conn;

    public NFTServices(Connection conn) {
        this.conn = conn;
    }

    public List<NFT> listArts() {
        Statement stmt = null;

        List<NFT> artList = new ArrayList<NFT>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT   \n" +
                    "   image,\n" +
                    "    a.title,\n" +
                    "    price,\n" +
                    "    c.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname,\n" +
                    "\ta.forsale\n" +
                    "FROM collection c\n" +
                    "         JOIN art a\n" +
                    "              ON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "                   AND a.\"forsale\" = true \n"+
                    "         JOIN userapp u\n" +
                    "              ON u.\"user_id\" = c.\"user_id\";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String collection = rs.getString(4);
                int price = rs.getInt(3);
                String title = rs.getString(2);
                String author = rs.getString(5) + " " + rs.getString(6);
                boolean forSale = rs.getBoolean(7);;

                artList.add(new NFT(id, collection, title, author, price, forSale));
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
        return artList;
    }

    public NFT getNft(String image){

        PreparedStatement stmt = null;

        NFT nft = null;

        try {
            String sql = "SELECT\n" +
                    "    image,\n" +
                    "    a.title,\n" +
                    "\tprice,\n" +
                    "\tc.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname,\n" +
                    "\ta.forsale\n" +
                    "    \tFROM collection c\n" +
                    "        JOIN art a\n" +
                    "        \tON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "        JOIN userapp u \n" +
                    "        \tON u.\"user_id\" = c.\"user_id\"\n" +
                    "\t\t\t\tAND a.image = ?;";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, image);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            String id = rs.getString(1);
            String collection = rs.getString(4);
            int price = rs.getInt(3);
            String title = rs.getString(2);
            String author = rs.getString(5) + " " + rs.getString(6);
            boolean forSale = rs.getBoolean(7);

            nft = new NFT(image,collection,title,author,price,forSale);
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
        return nft;
    }

    public NFT changeForSale(NFT art) {

        PreparedStatement stmt = null;
        NFT updatedArt = null;

        if (art != null) {

            try {

                stmt = this.conn.prepareStatement("UPDATE art SET forsale = ? WHERE image = ?;");


                stmt.setBoolean(1, (!art.isForSale()));
                stmt.setString(2, art.getId());

                stmt.executeUpdate();

                stmt = this.conn.prepareStatement("SELECT * FROM art WHERE image = ?;");
                stmt.setString(1, art.getId());
                ResultSet rs = stmt.executeQuery();


                rs.next();

                updatedArt = getNft(art.getId());

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
            return updatedArt;
        } else {
            return null;
        }
    }

    public int getIdCollection (String email, String collection){

        PreparedStatement stmt = null;
        int collection_id = 0;

        try {

            String sql = "SELECT \n" +
                    "\tcollection_id \n" +
                    "FROM collection  \n" +
                    "\t\tWHERE \"user_id\" = ?\n" +
                    "\t\tAND \"title\" = ?;";

            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, collection);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            collection_id = rs.getInt("collection_id");
            rs.close();
            stmt.close();
        }catch (SQLException se) {
            se.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    return collection_id;
    }
}
