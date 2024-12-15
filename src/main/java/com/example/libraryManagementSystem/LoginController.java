package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.DatabaseConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.libraryManagementSystem.MainApplication.loggedInUserIndex;
import static com.example.libraryManagementSystem.MainApplication.userList;

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
        loggedInUserIndex = 0;
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

            if (userRepository.authenticateUser(username, password)) {
                User user = userRepository.getUserByUsername(username);

                if (user != null) {
                    MainApplication.loggedInUserId = UserRepository.getUserIdByUsername(username);

                    // Route to appropriate view
                    if (user.getRole().equals(Role.ADMIN)) {
                        HelperFunctions.switchScene("adminWelcome");
                    } else {
                        HelperFunctions.switchScene("userWelcome");
                    }
                } else {
                    showError("User not found");
                }
            } else {
                showError("Invalid username or password");
            }
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
