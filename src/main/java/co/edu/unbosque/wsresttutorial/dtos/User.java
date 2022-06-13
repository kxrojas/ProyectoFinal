package co.edu.unbosque.dtos;

public class User {

    private String username;
    private String role;
    private String password;
    private String profileImage;

    //Método constructor de la clase User
    public User(String username, String role, String password, String s, String s1) {}

    //Método constructor de la clase User con variables declaradas
    public User(String username,  String role, String password, String profileImage) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.profileImage = profileImage;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}

