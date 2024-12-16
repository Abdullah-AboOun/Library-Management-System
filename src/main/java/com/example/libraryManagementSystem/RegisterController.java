package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.UserRepository;
import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Label confirmPasswordErrorLabel;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label fullNameErrorLabel;

    @FXML
    private TextField fullNameField;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label phoneErrorLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label userNameErrorLabel;

    @FXML
    private TextField userNameField;

    private String imagePath = MainApplication.defaultImagePath;
    UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.getItems().addAll("ADMIN", "USER", "LIBRARIAN");
        roleComboBox.setValue("USER");
        roleComboBox.setDisable(true);
        userRepository = new UserRepository();
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
    }

    @FXML
    void registerButtonOnClick(ActionEvent actionEvent) {
        boolean isValid = true;
        if (fullNameField.getText().isEmpty()) {
            fullNameErrorLabel.setText("Full name is required");
            isValid = false;
        } else {
            fullNameErrorLabel.setText("");
        }

        if (userNameField.getText().isEmpty()) {
            userNameErrorLabel.setText("Username is required");
            isValid = false;
        } else {
            userNameErrorLabel.setText("");
        }
        try {
            if (userRepository.isUsernameExists(userNameField.getText())) {
                userNameErrorLabel.setText("Username already exists");
                isValid = false;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while checking username");
            isValid = false;
        }

        if (passwordField.getText().isEmpty()) {
            passwordErrorLabel.setText("Password is required");
            isValid = false;
        } else {
            passwordErrorLabel.setText("");
        }

        if (confirmPasswordField.getText().isEmpty()) {
            confirmPasswordErrorLabel.setText("Confirm password is required");
            isValid = false;
        } else if (!confirmPasswordField.getText().equals(passwordField.getText())) {
            confirmPasswordErrorLabel.setText("Passwords do not match");
            isValid = false;
        } else {
            confirmPasswordErrorLabel.setText("");
        }

        if (emailField.getText().isEmpty()) {
            emailErrorLabel.setText("Email is required");
            isValid = false;
        } else {
            emailErrorLabel.setText("");
        }

        if (phoneField.getText().isEmpty()) {
            phoneErrorLabel.setText("Phone is required");
            isValid = false;
        } else {
            phoneErrorLabel.setText("");
        }

        if (!isValid) {
            return;
        }
        User user = new User(userNameField.getText(), passwordField.getText(), fullNameField.getText(),
                Role.USER, emailField.getText(), phoneField.getText(), imagePath);
        try {
            userRepository.addUser(user);
        } catch (SQLException e) {
            System.err.println("Error filtering users: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error",
                    "An error occurred while registering user");
        }
        HelperFunctions.switchScene("login");

    }

    @FXML
    void switchToLogin(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }

    @FXML
    void imageViewOnClick(MouseEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        String projectPath = System.getProperty("user.dir");
        File initialDirectory = new File(
                projectPath + "/src/main/resources/com/example/libraryManagementSystem/images");
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
        }
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
    }

}
