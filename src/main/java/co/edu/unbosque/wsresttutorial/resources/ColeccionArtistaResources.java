package co.edu.unbosque.resources;

import co.edu.unbosque.dtos.*;
import co.edu.unbosque.services.*;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/users/{username}/collections")
public class ColeccionArtistaResources {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "0000";

    @GET
    @Produces("application/json")
    public Response listUsers(@PathParam("username") String username) {

        Connection conn = null;
        List<Collection> collectionList = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);
            collectionList = collectionServices.listUserCollections(username);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); //
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok().entity(collectionList).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newCollection(@PathParam("username") String username, @FormParam("collection") String collection) throws IOException {
        Connection conn = null;
        List<Collection> collectionList = null;
        Collection c1 = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);

            c1 = new Collection(
                    username,
                    collection
            );
            collectionServices.newCollection(c1);

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
                .entity(c1)
                .build();
    }

    @GET
    @Path("/{collection}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollection(@PathParam("username") String username, @PathParam("collection") String collection) {
        Connection conn = null;
        Collection c1 = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);
            c1 = collectionServices.getCollection(username, collection);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Handling errors from JDBC driver
        } finally {
            // Cleaning-up environment
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if (c1 != null) {
            return Response.ok()
                    .entity(c1)
                    .build();
        } else {
            return Response.status(404)
                    .entity(new ExceptionMessage(404, "User not found"))
                    .build();
        }
    }
}
