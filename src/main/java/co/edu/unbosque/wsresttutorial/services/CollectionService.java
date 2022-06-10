package co.edu.unbosque.wsresttutorial.services;

import co.edu.unbosque.wsresttutorial.dtos.Collection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CollectionService {

    private Connection conn;

    public CollectionService() {}

    public CollectionService(Connection conn) {
        this.conn = conn;
    }

    public List<Collection> listCollection() {

        Statement statement = null;
        List<Collection> collectionList = new ArrayList<Collection>();

        try {
            String sql = "SELECT * FROM collection";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String title = resultSet.getString("title");

                collectionList.add(new Collection(userId, title));
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return collectionList;
    }
}
