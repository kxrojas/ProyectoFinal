package co.edu.unbosque.dtos;

public class Likes {

    private String username;
    private String nft_id;

    public Likes() {}

    public Likes(String username, String nft_id) {
        this.username = username;
        this.nft_id = nft_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNft_id() {
        return nft_id;
    }

    public void setNft_id(String nft_id) {
        this.nft_id = nft_id;
    }
}
