package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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

        }
    }

//    public static List<Book> getBorrowedBooks() {
//        List books = MainApplication.userBookMap.get(MainApplication.userList.get(MainApplication.loggedInUserIndex));
//        if (books == null) {
//            MainApplication.userBookMap.put(MainApplication.userList.get(MainApplication.loggedInUserIndex), List.of());
//            return null;
//        }
//        return books;
//    }
//
//    public static void addBorrowedBook(Book book) {
//        List<Book> books = getBorrowedBooks();
//        books.add(book);
//        MainApplication.userBookMap.put(MainApplication.userList.get(MainApplication.loggedInUserIndex), books);
//    }
}
