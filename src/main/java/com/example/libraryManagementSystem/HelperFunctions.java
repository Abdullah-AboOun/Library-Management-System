package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.UserRepository;
import com.example.libraryManagementSystem.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class HelperFunctions {
    private static Stage primaryStage;

    static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    static void setScene(String fxmlPath, String title, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelperFunctions.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchScene(String sceneName) {
        switch (sceneName) {
            case "login":
                setScene("login-view.fxml", "Login", 300, 400);
                break;
            case "register":
                setScene("register-view.fxml", "Register", 500, 650);
                break;
            case "adminDashboard":
                setScene("admin-dashboard-view.fxml", "Admin Dashboard", 1300, 700);
                break;
            case "profileDashboard":
                setScene("profile-dashboard-view.fxml", "Profile Dashboard", 600, 700);
                break;
            case "bookDashboard":
                setScene("books-dashboard-view.fxml", "Books Dashboard", 1400, 700);
                break;
            case "bookCategoryDashboard":
                setScene("books-category-view.fxml", "Add Category", 300, 300);
                break;
            case "userDashboard":
                setScene("user-dashboard-view.fxml", "User Dashboard", 900, 700);
                break;
            case "adminWelcome":
                setScene("admin-welcome-view.fxml", "Welcome", 700, 500);
                break;
            case "userWelcome":
                setScene("user-welcome-view.fxml", "Welcome", 700, 500);
                break;
            case "librarianWelcome":
                setScene("librarian-welcome-view.fxml", "Welcome", 700, 500);
                break;
            case "borrowManagement":
                setScene("borrow-management-view.fxml", "Borrow Management", 1100, 700);
                break;
            case "adminStatisticsDashboard":
                setScene("admin-statistics-view.fxml", "Statistics", 1000, 700);
                break;
            case "librarianStatisticsDashboard":
                setScene("librarian-statistics-view.fxml", "Statistics", 1000, 700);
                break;
            case "userBorrowed":
                setScene("user-borrowed-view.fxml", "Borrowed Books", 1000, 700);
                break;
        }
    }

    public static void runPythonScript(String scriptName) {
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptName);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static User getLoggedInUser() {
        UserRepository userRepository = new UserRepository();
        User loggedInUser;
        try {
            loggedInUser = userRepository.getUserById(MainApplication.loggedInUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loggedInUser;
    }

    public static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
