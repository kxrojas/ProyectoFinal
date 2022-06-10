package co.edu.unbosque.wsresttutorial.resources;

import co.edu.unbosque.wsresttutorial.dtos.ExceptionMessage;
import co.edu.unbosque.wsresttutorial.dtos.NFT;
import co.edu.unbosque.wsresttutorial.dtos.User;
import co.edu.unbosque.wsresttutorial.services.NFTService;
import co.edu.unbosque.wsresttutorial.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/nfts")
public class NFTResource {

    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "0987";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){

        Connection conn = null;
        List<NFT> nfts = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            NFTService nftService = new NFTService(conn);
            nfts = nftService.getNFTS();

            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Response.ok().entity(nfts).build();
    }









}
