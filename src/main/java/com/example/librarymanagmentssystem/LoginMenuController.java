package com.example.librarymanagmentssystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginMenuController {
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void submitButtonOnClick() {

        if (userNameField.getText().equals("aashgar") && passwordField.getText().equals("123456")) {
            errorLabel.setText("Welcome " + userNameField.getText());
            errorLabel.setStyle("-fx-text-fill: green;");
            //

        } else {
            errorLabel.setText("Invalid user name or password");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void registerButtonOnClick() {
        // make a registration screen

    }
}
