package co.edu.unbosque.wsresttutorial.dtos;

import com.opencsv.bean.CsvBindByName;

public class NFT {

    private String id;
    private String collection;
    private String title;
    private String autor;
    private float price;
    private boolean forSale;
    private String username;

    public NFT(){
    }

    public NFT(String id, String collection, String title, String autor, float price, boolean forSale, String username) {
        this.id = id;
        this.collection = collection;
        this.title = title;
        this.autor = autor;
        this.price = price;
        this.forSale = forSale;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
