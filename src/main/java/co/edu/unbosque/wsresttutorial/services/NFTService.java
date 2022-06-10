package co.edu.unbosque.wsresttutorial.services;

import co.edu.unbosque.wsresttutorial.dtos.NFT;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NFTService {

    private Connection conn;

    public NFTService(Connection conn) {
        this.conn = conn;
    }

    public List<NFT> getNFTS() {
        Statement statement = null;
        List<NFT> nftsList = new ArrayList<NFT>();

        try {
            statement = conn.createStatement();
            String sql = "SELECT image, nft.title, price, col.userId, col.title, user.username, nft.forSale FROM Collection col" +
                    "JOIN art nft ON nft.\"collectionId\" = col.\"collectionId\" AND nft.\"forSale\" = true" +
                    "JOIN userApp user On user.\"userId\" = col.\"userId\";";

            ResultSet resultSet =statement.executeQuery(sql);

            while (resultSet.next()) {

                String id = resultSet.getString(1);
                String title = resultSet.getString(2);
                int price = resultSet.getInt(3);
                String username = resultSet.getString(4);
                String collection = resultSet.getString(5);
                String autor = resultSet.getString(6) +""+ resultSet.getString(7);
                boolean forSale = resultSet.getBoolean(8);

                nftsList.add(new NFT(id, collection, title, autor, price, forSale, username));
            }

        }
        catch (SQLException e) {}
        finally {
            try {
                if (statement != null){
                    statement.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return nftsList;
    }


}
