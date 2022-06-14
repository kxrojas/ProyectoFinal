package co.edu.unbosque.dtos;

public class UserApp {

    private String username;
    private String role;
    private String password;

    //Método constructor de la clase User
    public UserApp(String username, String role, String password, String s, String s1) {}

    //Método constructor de la clase User con variables declaradas
    public UserApp(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

