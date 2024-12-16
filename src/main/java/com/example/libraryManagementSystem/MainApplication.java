package com.example.libraryManagementSystem;

import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class MainApplication extends Application {

    static String defaultImagePath = "";
    static String defaultBookImagePath = "";
    static int loggedInUserId;

    public static void main(String[] args) {
        try {
            HelperFunctions.runPythonScript("setup_db.py");
        } catch (Exception e) {
            System.err.println("Warning: Failed to execute Python script. Continuing without it.");
            e.printStackTrace();
        }
        launch(args);
    }

    private static void initializeData() {
        URL resourceUrl = MainApplication.class.getResource("/com/example/libraryManagementSystem/images/default.png");
        try {
            assert resourceUrl != null;
            defaultImagePath = new File(resourceUrl.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        resourceUrl = MainApplication.class.getResource("/com/example/libraryManagementSystem/images/book.png");
        try {
            assert resourceUrl != null;
            defaultBookImagePath = new File(resourceUrl.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        HelperFunctions.setPrimaryStage(stage);
        HelperFunctions.setScene("login-view.fxml", "Login", 300, 400);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/login.png"))));
        stage.show();
        initializeData();
    }
}