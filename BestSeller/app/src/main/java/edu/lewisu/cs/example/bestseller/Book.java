package edu.lewisu.cs.example.bestseller;

public class Book {
    private String title;
    private String author;
    private String amazon;
    private String description;

    public Book(String title, String author, String amazon, String description) {
        this.title = title;
        this.author = author;
        this.amazon = amazon;
        this.description = description;
    }

    public Book(String title) {

        this.title = title;
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

    public String getAmazon() {
        return amazon;
    }

    public void setAmazon(String amazon) {
        this.amazon = amazon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
