package co.edu.unbosque.resources;

import co.edu.unbosque.dtos.*;
import co.edu.unbosque.services.NFTServices;

import co.edu.unbosque.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UsersResource {

    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "profileImages";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/TiendaVale";
    static final String USER = "postgres";
    static final String PASS = "0000";

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForm(
            @FormParam("password") String password,
            @FormParam("username") String username,
            @FormParam("role") String role
    ) {
        Connection conn = null;
        UserApp user = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            System.out.println(password);
            System.out.println(username);
            System.out.println(role);

            user = new UserApp(password, username, role);
            usersService.newUserApp(user);

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
                .entity(user)
                .build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresar(UserApp userApp){
        Connection conn = null;
        UserApp user = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            new UserService(conn);
            List<UserApp> userAppList = new ArrayList<UserApp>();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return Response.ok().entity(userApp).build();
    }

    @GET
    @Produces("application/json")
    public Response listUsers() {
        Connection conn = null;
        List<User> userList = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            userList = usersService.listUsers();

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
        return Response.ok().entity(userList).build();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {

        Connection conn = null;
        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            user = usersService.getUser(username);

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
        if (user != null) {
            return Response.ok()
                    .entity(user)
                    .build();
        } else {
            return Response.status(404)
                    .entity(new ExceptionMessage(404, "User not found"))
                    .build();
        }

    }

    @GET
    @Path("/{username}/collections/{collection}/arts/{art}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArt(@PathParam("username") String username, @PathParam("art") String image) {
        Connection conn = null;

        NFT art = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            art = new NFTServices(conn).getNft(image);
            conn.close();
        } catch (ClassNotFoundException | SQLException nullPointerException) {
            return Response.ok()
                    .entity(String.valueOf(0))
                    .build();
        }

        return Response.ok()
                .entity(art)
                .build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForm(MultipartFormDataInput inputData) {
        String username = "";
        Connection conn = null;
        User user = null;

        try {
            username = inputData.getFormDataPart("username", String.class, null);
            String password = inputData.getFormDataPart("password", String.class, null);
            String role = inputData.getFormDataPart("role", String.class, null);

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService userService = new UserService(conn);

            Map<String, List<InputPart>> formParts = inputData.getFormDataMap();
            List<InputPart> inputParts = formParts.get("formFile");

            for (InputPart inputPart : inputParts) {
                try {
                    MultivaluedMap<String, String> headers = inputPart.getHeaders();
                    InputStream istream = inputPart.getBody(InputStream.class, null);

                    saveFile(istream, "", context);
                    user = new User(username, role, password, "", "");
                    userService.newUser(user);
                    conn.close();

                } catch (IOException e) {
                    return Response.serverError().build();
                }
            }
        } catch (IOException e) {
            return Response.serverError().build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                .entity(user)
                .build();
    }

    private void saveFile(InputStream uploadedInputStream, String fileName, ServletContext context) {
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            String uploadPath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator;

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            OutputStream outpuStream = new FileOutputStream(uploadPath + fileName);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}