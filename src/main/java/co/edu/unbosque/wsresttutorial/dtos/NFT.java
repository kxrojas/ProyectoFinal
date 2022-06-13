package co.edu.unbosque.dtos;

public class NFT {

    private String id;
    private String collection;
    private String title;
    private String author;
    private long price;
    private boolean forSale;

    public NFT() {}

    public NFT(String id, String collection, String title, String author, long price, boolean forSale) {
        this.id = id;
        this.collection = collection;
        this.title = title;
        this.author = author;
        this.price = price;
        this.forSale = forSale;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }
}
