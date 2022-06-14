package co.edu.unbosque.resources;

import co.edu.unbosque.dtos.*;
import co.edu.unbosque.services.*;

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

@Path("/owners")
public class OwnershipResources {

    @Context
    ServletContext context;

    private String UPLOAD_DIRECTORY = "NFTS";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "0000";

    @Path("/arts/{art}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnerArt(@PathParam("art") String art) throws IOException {

        Connection conn = null;
        User user = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            OwnershipServices ownershipServices = new OwnershipServices(conn);

            String emailOwner = ownershipServices.getOwnership(art);

            UserService userService = new UserService(conn);
            user = userService.getUser(emailOwner);

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
        return Response.ok().entity(user).build();
    }

    @Path("/{username}/arts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListArtsOwner(@PathParam("username") String username) throws IOException {

        Connection conn = null;
        List<NFT> nfts = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            OwnershipServices ownershipServices = new OwnershipServices(conn);

            nfts = ownershipServices.getListArtsOwnership(username);

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
        List<NFT> dataFiles = new ArrayList<NFT>();

        for(int j=0;j<nfts.size();j++){
            dataFiles.add(nfts.get(j));
            dataFiles.get(j).setId(UPLOAD_DIRECTORY + File.separator + dataFiles.get(j).getId());
        }
        return Response.ok().entity(nfts).build();
    }


    @Path("/{username}/arts/{art}")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public Response buyArt(@PathParam("username") String username, @PathParam("art") String art)
            throws IOException {
        Connection  conn = null;
        String result = "";
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            result = new OwnershipServices(conn).buyNft(username,art);

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
        return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                .entity(result)
                .build();
    }

}
