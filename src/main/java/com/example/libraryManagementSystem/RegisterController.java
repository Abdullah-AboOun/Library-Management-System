package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField fullNameField;
    public Label fullNameErrorLabel;
    public TextField userNameField;
    public Label userNameErrorLabel;
    public PasswordField passwordField;
    public Label passwordErrorLabel;
    public PasswordField confirmPasswordField;
    public Label confirmPasswordErrorLabel;
    public TextField emailField;
    public Label emailErrorLabel;
    public TextField phoneField;
    public Label phoneErrorLabel;
    public ComboBox roleComboBox;
    public Label roleErrorLabel;
    public ImageView profileImageView;
    private String imagePath = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.getItems().addAll("Admin", "User", "Librarian");
        URL resourceUrl = getClass().getResource("/com/example/libraryManagementSystem/images/avatar.png");
        try {
            assert resourceUrl != null;
            imagePath = new File(resourceUrl.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
    }

    public void registerButtonOnClick(ActionEvent actionEvent) {
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
        if (MainApplication.userList.parallelStream()
                .anyMatch(user -> user.getUserName().equals(userNameField.getText()))) {
            userNameErrorLabel.setText("Username already exists");
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

        if (roleComboBox.getSelectionModel().isEmpty()) {
            roleErrorLabel.setText("Role is required");
            isValid = false;
        } else {
            roleErrorLabel.setText("");
        }

        if (isValid) {
            String role = roleComboBox.getSelectionModel().getSelectedItem().toString();

            Role userRole = switch (role) {
                case "Admin" -> Role.ADMIN;
                case "Librarian" -> Role.Librarian;
                default -> Role.USER;
            };

            User user = new User(userNameField.getText(), passwordField.getText(), fullNameField.getText(),
                    userRole, emailField.getText(), phoneField.getText(), imagePath);
            MainApplication.userList.add(user);
            System.out.println(MainApplication.userList);
            HelperFunctions.switchScene("login");
        }

    }

    public void switchToLogin(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }

    public void imageViewOnClick(MouseEvent actionEvent) {
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
        System.out.println(imagePath);
    }

}
