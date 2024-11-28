package com.example.libraryManagementSystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButtonOnClick();
            }
        });
    }

    @FXML
    private void loginButtonOnClick() {
        boolean userExists = MainApplication.userList.parallelStream()
                .anyMatch(user -> user.getUserName().equals(userNameField.getText())
                        && user.getPassword().equals(passwordField.getText()));

        if (userExists) {
            errorLabel.setText("Welcome " + userNameField.getText());
            errorLabel.getStyleClass().setAll("success-label");
        } else {
            errorLabel.setText("⚠ Invalid username or password");
            errorLabel.getStyleClass().setAll("error-label");
        }
    }

    @FXML
    private void registerButtonOnClick() {
        HelperFunctions.switchScene("register");

    }

}
