package com.example.libraryManagementSystem;

public class BorrowManagementTable {
    private int userId;
    private String userName;
    private String userImage;
    private int bookId;
    private String bookTitle;
    private String bookImage;
    private String borrowStatus;

    public BorrowManagementTable(int userId, String userName, String userImage,
                                 int bookId, String bookTitle, String bookImage, String borrowStatus) {
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.borrowStatus = borrowStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }
}
