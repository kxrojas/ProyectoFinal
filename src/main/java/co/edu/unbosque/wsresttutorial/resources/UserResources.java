package co.edu.unbosque.resources;

import co.edu.unbosque.dtos.*;
import co.edu.unbosque.services.*;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Path("/users/{username}/collections/{collection}/arts")
public class UserResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "0000";

    private NFTServices NFTServices;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listas(@PathParam("username") String username, @PathParam("collection") String collectionName) {

        Connection conn = null;
        List<NFT> nftList = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            NFTServices = new NFTServices(conn);
            nftList = NFTServices.listArts2();

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

        List<NFT> nfts = new ArrayList<NFT>();

        for (NFT nft : nftList) {

            if (nft.getAuthor().equals(username) && nft.getCollection().equals(collectionName)) {
                nft.setId(UPLOAD_DIRECTORY + File.separator + nft.getId());
                nfts.add(nft);
            }
        }
        return Response.ok().entity(nfts).build();
    }
}
