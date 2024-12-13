package com.example.libraryManagementSystem;

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

        User user = null;
        String query = "SELECT * FROM users WHERE userName = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userNameField.getText());
            preparedStatement.setString(2, passwordField.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("userName"),
                        resultSet.getString("password"),
                        resultSet.getString("fullName"),
                        Role.valueOf(resultSet.getString("role")),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("imagePath")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user == null) {
            errorLabel.setGraphic(new FontIcon(Material2AL.ERROR));
            errorLabel.setText("Invalid username or password");
            return;
        }
        loggedInUserIndex = userList.indexOf(user);
        if (user.getRole().equals(Role.ADMIN)) {
            HelperFunctions.switchScene("adminWelcome");
        } else {
            HelperFunctions.switchScene("userWelcome");
        }
    }

    @FXML
    void registerButtonOnClick() {
        HelperFunctions.switchScene("register");

    }

}
