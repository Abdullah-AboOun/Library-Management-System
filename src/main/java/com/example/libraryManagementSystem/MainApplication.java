package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static javafx.collections.FXCollections.observableArrayList;

public class MainApplication extends Application {

    static ObservableList<User> userList = observableArrayList();
    static int loggedInUserIndex = 0;
    static String defaultImagePath = "";

    public static void main(String[] args) {
        launch(args);
    }

    private static void initializeData() {
        URL resourceUrl = MainApplication.class.getResource("/com/example/libraryManagementSystem/images/avatar.png");
        try {
            assert resourceUrl != null;
            defaultImagePath = new File(resourceUrl.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        userList.addAll(
                new User("ahmad", "123", "Ahmad", Role.ADMIN, "ahmad@gmail.com",
                        "123456789", defaultImagePath),
                new User("ali", "123", "Ali", Role.USER, "ali@gmail.com",
                        "123456789", defaultImagePath),
                new User("sara", "123", "Sara", Role.Librarian, "Sara@gmail.com",
                        "123456789", defaultImagePath));
        System.out.println(defaultImagePath);
    }

    @Override
    public void start(Stage stage) throws IOException {
        HelperFunctions.setPrimaryStage(stage);
        HelperFunctions.setScene("login-view.fxml", "Login", 300, 400);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/login.png"))));
        stage.show();
        initializeData();
    }
}