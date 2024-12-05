package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

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
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButtonOnClick();
            }
        });
        loggedInUserIndex = 0;
        loginButton.setGraphic(new FontIcon(Material2AL.LOGIN));

    }

    @FXML
    private void loginButtonOnClick() {

        User user = userList.parallelStream().filter(u -> u.getUserName().equals(userNameField.getText()) && u.getPassword().equals(passwordField.getText())).findFirst().orElse(null);

        if (user == null) {
            errorLabel.setGraphic(new FontIcon(Material2AL.ERROR));
            errorLabel.setText("Invalid username or password");
            return;
        }
        loggedInUserIndex = userList.indexOf(user);
        HelperFunctions.switchScene("dashboard");
    }

    @FXML
    private void registerButtonOnClick() {
        HelperFunctions.switchScene("register");

    }

}
