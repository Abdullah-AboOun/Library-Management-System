package com.example.libraryManagementSystem;

import atlantafx.base.theme.Styles;
import com.example.libraryManagementSystem.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.libraryManagementSystem.MainApplication.loggedInUserIndex;
import static com.example.libraryManagementSystem.MainApplication.userList;

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
        loggedInUserIndex = 0;
    }

    @FXML
    private void loginButtonOnClick() {

        User user = userList.parallelStream()
                .filter(u -> u.getUserName().equals(userNameField.getText())
                        && u.getPassword().equals(passwordField.getText()))
                .findFirst().orElse(null);

        if (user == null) {
            errorLabel.setText("⚠ Invalid username or password");
            passwordField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            userNameField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            return;
        }
        loggedInUserIndex = userList.indexOf(user);
        passwordField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        userNameField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        HelperFunctions.switchScene("dashboard");
    }

    @FXML
    private void registerButtonOnClick() {
        HelperFunctions.switchScene("register");

    }

}
