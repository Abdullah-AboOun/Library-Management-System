package com.example.libraryManagementSystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
            case "dashboard":
                setScene("dashboard-view.fxml", "Dashboard", 1200, 600);
                break;
            case "librarianDashboard":
                setScene("librarian-dashboard-view.fxml", "Librarian Dashboard", 800, 600);
                break;
        }
    }
}
