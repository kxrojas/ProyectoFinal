package co.edu.unbosque.resources;

import co.edu.unbosque.dtos.ExceptionMessage;
import co.edu.unbosque.services.WalletServices;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Path("users/{username}/wallet")
public class WalletResources {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://199.223.235.245/near";
    static final String USER = "postgres";
    static final String PASS = "near123";

    @GET
    @Path("/fcoins")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserFCoins(@PathParam("username") String username) throws IOException {

        Connection conn = null;
        JSONObject fcoins = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            WalletServices walletService = new WalletServices(conn);
            fcoins = walletService.getFcoinsUser(username);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if (fcoins == null) {
            return Response.status(404).entity(new ExceptionMessage(404, "User not found")).build();
        }
        return Response.ok().entity(fcoins).build();
    }
}