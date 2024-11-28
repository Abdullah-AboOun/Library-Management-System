package com.example.librarymanagmentssystem;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LoginMenuController {
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void loginButtonOnClick() {
        boolean userExists = MainApplication.userList.parallelStream().anyMatch(user -> user.getUserName().equals(userNameField.getText()) && user.getPassword().equals(passwordField.getText()));

        if (userExists) {
            errorLabel.setText("Welcome " + userNameField.getText());
            errorLabel.getStyleClass().setAll("success-label");
        } else {
            errorLabel.setOpacity(0);
            errorLabel.setText("⚠ Invalid username or password");
            errorLabel.getStyleClass().setAll("error-label");

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), errorLabel);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), errorLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setDelay(Duration.seconds(1));
        fadeTransition.play();
    }

    @FXML
    private void registerButtonOnClick() {
        HelperFunctions.switchScene("register");

    }
}
