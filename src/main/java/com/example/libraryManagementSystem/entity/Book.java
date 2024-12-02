package com.example.libraryManagementSystem.entity;

public class Book {
    private String title;
    private String author;
    private String dateOfPublication;
    private String ISBN;
    private String language;
    private String category;
    private String publisher;
    private String imagePath;
    private int pagesNumber;
    private int copiesNumber;

    public Book(String title, String author, String dateOfPublication, String ISBN, String language, String category,
                String publisher, String imagePath, int pagesNumber, int copiesNumber) {
        this.title = title;
        this.author = author;
        this.dateOfPublication = dateOfPublication;
        this.ISBN = ISBN;
        this.language = language;
        this.category = category;
        this.publisher = publisher;
        this.imagePath = imagePath;
        this.pagesNumber = pagesNumber;
        this.copiesNumber = copiesNumber;
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

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public int getCopiesNumber() {
        return copiesNumber;
    }

    public void setCopiesNumber(int copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dateOfPublication='" + dateOfPublication + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", language='" + language + '\'' +
                ", category='" + category + '\'' +
                ", publisher='" + publisher + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", copiesNumber=" + copiesNumber +
                '}';
    }
}
