package co.edu.unbosque.services;

import co.edu.unbosque.dtos.WalletHistory;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WalletServices {

    private Connection conn;

    public WalletServices(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<WalletHistory> getWalletHistoryUser(String username) {

        PreparedStatement stmt = null;
        ArrayList<WalletHistory> walletHistory = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM wallet_history WHERE user_id = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                walletHistory.add(new WalletHistory(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4), rs.getString(5),
                        rs.getDate(6)));
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
        return walletHistory;
    }

    public JSONObject getFcoinsUser(String username){

        PreparedStatement stmt = null;
        JSONObject fcoins = null;

        if (username != null) {
            try {

                String sql = "SELECT\n" +
                        "    u.user_id,\n" +
                        "    SUM (fcoins) AS fcoins\n" +
                        "FROM wallet_history w\n" +
                        "         JOIN userapp u \n" +
                        "              ON u.user_id = w.user_id\n" +
                        "                  AND w.user_id = ?\n" +
                        "GROUP BY u.user_id;";

                stmt = this.conn.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    fcoins = new JSONObject();
                    fcoins.put("username", username);
                    fcoins.put("fcoins", rs.getString("fcoins"));
                }
                if(fcoins == null){
                    fcoins = new JSONObject();
                    fcoins.put("username", username);
                    fcoins.put("fcoins", 0);
                }
                rs.close();
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
            return fcoins;
        }
        else {return null;}
    }
}
