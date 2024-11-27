package com.example.librarymanagmentssystem;

import com.example.librarymanagmentssystem.Entitiy.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        ArrayList<Person> person = new ArrayList<>();
        person.add(new Person("Admin1", "admin1", "123456", "admin@gmail.com", "123456789"));
        launch();
    }
}