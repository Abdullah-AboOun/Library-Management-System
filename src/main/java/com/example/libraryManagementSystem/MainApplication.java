package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.collections.FXCollections.observableArrayList;

public class MainApplication extends Application {

    static ObservableList<User> userList = observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        HelperFunctions.setPrimaryStage(stage);
        HelperFunctions.setScene("login-view.fxml", "Login", 300, 400);
        stage.setTitle("Library Management System");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/login.png"))));
        stage.show();
        initializeData();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void initializeData() {
        userList.addAll(
                new User(1, "Ahmad", "ahmad", "123", Role.ADMIN, "ahmad@gmail.com",
                        "123456789", "images/avatar.png"),
                new User(2, "Ali", "ali", "123", Role.USER, "ali@gmail.com",
                        "123456789", "images/avatar.png"),
                new User(3, "Sara", "sara", "123", Role.Librarian, "Sara@gmail.com",
                        "123456789", "images/avatar.png"));
    }
}