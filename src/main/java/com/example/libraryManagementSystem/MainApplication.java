package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Book;
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

    public static ObservableList<Book> bookList = observableArrayList();
    public static ObservableList<User> userList = observableArrayList();
    static int loggedInUserIndex = 0;
    static String defaultImagePath = "";
    static String defaultBookImagePath = "";

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
                new User("ali", "123", "Ali", Role.ADMIN, "ali@gmail.com",
                        "123456789", defaultImagePath),
                new User("sara", "123", "Sara", Role.LIBRARIAN, "Sara@gmail.com",
                        "123456789", defaultImagePath),
                new User("nada", "123", "Nada", Role.USER, "nada@gmail.com",
                        "123456789", defaultImagePath));

        resourceUrl = MainApplication.class.getResource("/com/example/libraryManagementSystem/images/open-book.png");
        try {
            assert resourceUrl != null;
            defaultBookImagePath = new File(resourceUrl.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        bookList.addAll(
                new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925", "35363636",
                        "English", "Literature", "Scribner", defaultBookImagePath,
                        180, 5),
                new Book("To Kill a Mockingbird", "Harper Lee", "1960", "35363636",
                        "English", "Literature", "J. B. Lippincott & Co.", defaultBookImagePath,
                        281, 3),
                new Book("1984", "George Orwell", "1949", "35363636",
                        "English", "Science Fiction", "Secker & Warburg", defaultBookImagePath,
                        328, 2),
                new Book("Pride and Prejudice", "Jane Austen", "1813", "35363636",
                        "English", "Literature", "T. Egerton, Whitehall", defaultBookImagePath,
                        279, 4),
                new Book("The Catcher in the Rye", "J. D. Salinger", "1951", "35363636",
                        "English", "Literature", "Little, Brown and Company", defaultBookImagePath,
                        234, 6),
                new Book("الأرض", "علي الطنطاوي", "2021", "35363636",
                        "Arabic", "Science", "المكتبة العصرية", defaultBookImagePath,
                        200, 10),
                new Book("Der Vorleser", "Bernhard Schlink", "1995", "35363636",
                        "German", "Literature", "Diogenes", defaultBookImagePath,
                        300, 7),
                new Book("L'Étranger", "Albert Camus", "1942", "35363636",
                        "French", "Literature", "Gallimard", defaultBookImagePath,
                        250, 8),
                new Book("El amor en los tiempos del cólera", "Gabriel García Márquez", "1985", "35363636",
                        "Spanish", "Literature", "Oveja Negra", defaultBookImagePath,
                        400, 9),
                new Book("Mathematics for the Nonmathematician", "Morris Kline", "1967", "35363636",
                        "English", "Math", "Dover Publications", defaultBookImagePath,
                        400, 9),
                new Book("A People's History of the United States", "Howard Zinn", "1980", "35363636",
                        "English", "History", "Harper & Row", defaultBookImagePath,
                        400, 9),
                new Book("العرب والعالم", "عبد العزيز الدريوش", "2010", "35363636",
                        "Arabic", "History", "المكتبة العصرية", defaultBookImagePath,
                        400, 9)

        );
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