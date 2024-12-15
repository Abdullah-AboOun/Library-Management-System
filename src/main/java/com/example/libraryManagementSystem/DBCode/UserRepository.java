package com.example.libraryManagementSystem.DBCode;

import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final DatabaseConnection dbConnection;

    public UserRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            System.out.println("Executing query for userId: " + userId); // Debug log

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
                System.out.println("No user found for id: " + userId); // Debug log
            }
        }
        return null;
    }

    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE userName = ?";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            System.out.println("Executing query for username: " + username); // Debug log

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
                System.out.println("No user found for username: " + username); // Debug log
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
            throw e;
        }
    }

    public boolean addUser(User user) throws SQLException {
        String query = """
                INSERT INTO users (userName, password, fullName, role, email, phoneNumber, imagePath)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setString(7, user.getImagePath());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateUser(User user) throws SQLException {
        String query = """
                UPDATE users
                SET password=?, fullName=?, role=?, email=?, phoneNumber=?, imagePath=? WHERE userName=?
                """;
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getRole().toString());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getImagePath());
            stmt.setString(7, user.getUserName());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(String username) throws SQLException {
        String query = "DELETE FROM users WHERE userName = ?";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = dbConnection.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        }
        return users;
    }

    public User authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE userName = ? AND password = ?";
        try (Connection connection = dbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    System.out.println("No user found for username: " + username); // Debug log
                    return null;
                }

            }
        } catch (SQLException e) {
            System.err.println("Authentication error for user " + username + ": " + e.getMessage());
            throw e;
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("userName"),
                rs.getString("password"),
                rs.getString("fullName"),
                Role.valueOf(rs.getString("role").toUpperCase()),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                rs.getString("imagePath"));
    }

    public static int getUserIdByUsername(String username) {
        String query = "SELECT id FROM users WHERE userName = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}