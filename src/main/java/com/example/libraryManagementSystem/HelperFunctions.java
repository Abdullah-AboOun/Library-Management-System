package com.example.libraryManagementSystem;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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
                setScene("admin-dashboard-view.fxml", "Admin Dashboard", 800, 600);
                break;
            case "userDashboard":
                setScene("user-dashboard-view.fxml", "User Dashboard", 800, 600);
                break;
            case "librarianDashboard":
                setScene("librarian-dashboard-view.fxml", "Librarian Dashboard", 800, 600);
                break;
        }
    }

    public static void saveUserImage(Image image, String fileName) {
        try {
            String resourcePath = "src/main/resources/com/example/libraryManagementSystem/images/users images";
            File outputFile = new File(resourcePath + fileName);
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
