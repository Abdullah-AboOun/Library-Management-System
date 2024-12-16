package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.UserRepository;
import com.example.libraryManagementSystem.entity.Role;
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
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButtonOnClick();
            }
        });
        loginButton.setGraphic(new FontIcon(Material2AL.LOGIN));

    }

    @FXML
    void loginButtonOnClick() {
        String username = userNameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password are required");
            return;
        }

        try {
            UserRepository userRepository = new UserRepository();
            User user = userRepository.authenticateUser(username, password);

            if (user == null) {
                showError("Invalid username or password");
                return;
            }

            MainApplication.loggedInUserId = UserRepository.getUserIdByUsername(username);
            HelperFunctions.switchScene(user.getRole() == Role.ADMIN ? "adminWelcome" : "userWelcome");

        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            showError("Database error occurred");
        }
    }

    @FXML
    void registerButtonOnClick() {
        HelperFunctions.switchScene("register");

    }

    private void showError(String message) {
        errorLabel.setGraphic(new FontIcon(Material2AL.ERROR));
        errorLabel.setText(message);
    }

}
