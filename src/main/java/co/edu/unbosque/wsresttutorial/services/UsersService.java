package co.edu.unbosque.wsresttutorial.services;

import co.edu.unbosque.wsresttutorial.dtos.User;
import com.opencsv.bean.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UsersService {

//   public List<User> getUsers() throws IOException {
//
  //      List<User> users;

    //    try (InputStream is = UsersService.class.getClassLoader()
      //          .getResourceAsStream("users.csv")) {

        //    HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
          //  strategy.setType(User.class);

            //try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

              //  CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(br)
                //        .withType(User.class)
                  //      .withMappingStrategy(strategy)
                    //    .withIgnoreLeadingWhiteSpace(true)
                      //  .build();

                //users = csvToBean.parse();
            //}
        //}

        //return users;
    //}

    //public User createUser(String username, String password, String role, String path) throws IOException {
      //  String newLine = "\n" + username + "," + password + "," + role;
        //FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes/" + "users.csv", true);
        //os.write(newLine.getBytes());
        //os.close();

        //return new User(username, password, role);
   // }
    private Connection conn;

    public UsersService(Connection conn) {
        this.conn = conn;
    }

            public List<User> listUsers() {
                // Object for handling SQL statement
                Statement stmt = null;

                // Data structure to map results from database
                List<User> users = new ArrayList<User>();

                try {
                    // Executing a SQL query
                    System.out.println("=> Lista de usuarios...");
                    stmt = conn.createStatement();

                    String sql = "SELECT username,password,role,fcoins FROM userapp";
                    ResultSet rs = stmt.executeQuery(sql);

                    // Reading data from result set row by row
                    while (rs.next()) {
                        // Extracting row values by column name

                        String name = rs.getString("username");
                        String password = rs.getString("password");
                        String role = rs.getString("role");


                        // Creating a new UserApp class instance and adding it to the array list
                        users.add(new User(name,password,role));
                    }

                    // Closing resources
                    rs.close();
                    stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(); // Handling errors from database
                } finally {
                    // Cleaning-up environment
                    try {
                        if (stmt != null) stmt.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
                return users;
            }


        }