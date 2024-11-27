package com.example.librarymanagmentssystem;

import com.example.librarymanagmentssystem.Entitiy.Role;
import com.example.librarymanagmentssystem.Entitiy.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.collections.FXCollections.observableArrayList;

public class MainApplication extends Application {

    static ObservableList<User> userList = observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        stage.setTitle("Login Screen");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/login.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        initializeData();

        launch(args);
    }

    private static void initializeData() {
        userList.addAll(
                new User(1, "Ahmad", "ahmad", "123", Role.ADMIN, "ahmad@gmail.com", "123456789"),
                new User(2, "Ali", "ali", "123", Role.USER, "ali@gmail.com", "123456789"),
                new User(3, "Sara", "sara", "123", Role.Librarian, "Sara@gmail.com", "123456789"));
    }
}