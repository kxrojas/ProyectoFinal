package co.edu.unbosque.wsresttutorial.resources;

import co.edu.unbosque.wsresttutorial.dtos.Collection;
import co.edu.unbosque.wsresttutorial.services.CollectionService;
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
public class CollectionResource {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "0987";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimasCollections() throws IOException {

        Connection conn = null;
        List<Collection> collections = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionService collectionService = new CollectionService(conn);
            collections = collectionService.listCollection();
            Collections.reverse(collections);
            if(collections.size()>3){
                int i=3;
                while (collections.size()>3) {
                    collections.remove(i);
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
        return Response.ok().entity(collections).build();
    }
}
