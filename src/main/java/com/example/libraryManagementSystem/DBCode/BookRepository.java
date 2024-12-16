package com.example.libraryManagementSystem.DBCode;

import com.example.libraryManagementSystem.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private final DatabaseConnection dbConnection;

    public BookRepository() {
        this.dbConnection = new DatabaseConnection();
    }

    public Book getBookByISBN(String isbn) throws SQLException {
        String query = "SELECT * FROM books WHERE ISBN = ?";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractBookFromResultSet(rs);
            }
        }
        return null;
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books ORDER BY title";

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (!connection.isValid(5)) {
                throw new SQLException("Database connection validation failed");
            }

            while (rs.next()) {
                try {
                    Book book = extractBookFromResultSet(rs);
                    books.add(book);
                } catch (SQLException e) {
                    System.err.println("Error extracting book from result set: " + e.getMessage());
                    // Continue processing other records even if one fails
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error in getAllBooks: " + e.getMessage());
            throw e;
        }

        return books;
    }

    public boolean addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, dateOfPublication, ISBN, language, category, publisher, imagePath, pagesNumber, copiesNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            setBookParameters(stmt, book);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateBook(Book book) throws SQLException {
        String query = "UPDATE books SET title=?, author=?, dateOfPublication=?, language=?, category=?, publisher=?, imagePath=?, pagesNumber=?, copiesNumber=? WHERE ISBN=?";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getDateOfPublication());
            stmt.setString(4, book.getLanguage());
            stmt.setString(5, book.getCategory());
            stmt.setString(6, book.getPublisher());
            stmt.setString(7, book.getImagePath());
            stmt.setInt(8, book.getPagesNumber());
            stmt.setInt(9, book.getCopiesNumber());
            stmt.setString(10, book.getISBN()); // WHERE clause parameter
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteBook(String isbn) throws SQLException {
        String query = "DELETE FROM books WHERE ISBN=?";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, isbn);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Book> getBooksByCategory(String category) throws SQLException {
        String query = "SELECT * FROM books WHERE category = ?";
        List<Book> books = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        }
        return books;
    }

    public List<String> getCategories() throws SQLException {
        String query = "SELECT name FROM book_categories ORDER BY name";
        List<String> categories = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("name"));
            }
        }
        return categories;
    }

    public boolean addCategory(String categoryName) throws SQLException {
        String query = "INSERT INTO book_categories (name) VALUES (?)";
        
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, categoryName);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                throw new SQLException("Category already exists");
            }
            throw e;
        }
    }
    public List<String> getLanguages() throws SQLException {
        String query = "SELECT name FROM book_languages ORDER BY name";
        List<String> languages = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                languages.add(rs.getString("name"));
            }
        }
        return languages;
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        return new Book(
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("dateOfPublication"),
                rs.getString("ISBN"),
                rs.getString("language"),
                rs.getString("category"),
                rs.getString("publisher"),
                rs.getString("imagePath"),
                rs.getInt("pagesNumber"),
                rs.getInt("copiesNumber"));
    }

    private void setBookParameters(PreparedStatement stmt, Book book) throws SQLException {
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getDateOfPublication());
        stmt.setString(4, book.getISBN());
        stmt.setString(5, book.getLanguage());
        stmt.setString(6, book.getCategory());
        stmt.setString(7, book.getPublisher());
        stmt.setString(8, book.getImagePath());
        stmt.setInt(9, book.getPagesNumber());
        stmt.setInt(10, book.getCopiesNumber());
    }
}