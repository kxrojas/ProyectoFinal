package co.edu.unbosque.resources;

import co.edu.unbosque.dtos.*;
import co.edu.unbosque.services.*;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Path("/collections")
public class ColeccionesResources {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:63342/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "5432";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColecciones() throws IOException {
        Connection conn = null;
        List<Collection> collectionList = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);
            collectionList = collectionServices.listCollections();
            Collections.reverse(collectionList);
            if(collectionList.size()>3){
                int i=3;
                    while (collectionList.size()>3) {
                        collectionList.remove(i);
                    }
            }
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
        return Response.ok().entity(collectionList).build();
    }

}
