package co.edu.unbosque.resources;

import co.edu.unbosque.dtos.NFT;
import co.edu.unbosque.services.NFTServices;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/arts")
public class NFTResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:63342/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "5432";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generalListFiles() {

        Connection conn = null;
        List<NFT> nftList = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            NFTServices NFTServices = new NFTServices(conn);
            nftList = NFTServices.listArts();

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
        return Response.ok().entity(nftList).build();
    }

    @PUT
    @Path("/forsale")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateForSale(NFT art) throws IOException {
        Connection conn = null;
        NFT updateArt = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            NFTServices NFTServices = new NFTServices(conn);
            updateArt = NFTServices.changeForSale(art);

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
        return Response.ok()
                .entity(updateArt)
                .build();
    }
}
