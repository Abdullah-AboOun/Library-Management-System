package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileDashboardController implements Initializable {

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
    private Label roleErrorLabel;

    @FXML
    private Label userNameErrorLabel;

    @FXML
    private TextField userNameField;

    private String imagePath;
    User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);

        roleComboBox.getItems().addAll("Admin", "User", "Librarian");

        fullNameField.setText(loggedInUser.getFullName());
        userNameField.setText(loggedInUser.getUserName());
        emailField.setText(loggedInUser.getEmail());
        phoneField.setText(loggedInUser.getPhoneNumber());
        passwordField.setText(loggedInUser.getPassword());
        confirmPasswordField.setText(loggedInUser.getPassword());
        roleComboBox.setValue(loggedInUser.getRole().toString());

        imagePath = loggedInUser.getImagePath();
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));


    }

    @FXML
    void updateButtonOnClick(ActionEvent actionEvent) {

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

        if (emailField.getText().isEmpty()) {
            emailErrorLabel.setText("Email is required");
            isValid = false;
        } else {
            emailErrorLabel.setText("");
        }

        if (phoneField.getText().isEmpty()) {
            phoneErrorLabel.setText("Phone number is required");
            isValid = false;
        } else {
            phoneErrorLabel.setText("");
        }

        if (passwordField.getText().isEmpty()) {
            passwordErrorLabel.setText("Password is required");
            isValid = false;
        } else {
            passwordErrorLabel.setText("");
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            confirmPasswordErrorLabel.setText("Passwords do not match");
            isValid = false;
        } else {
            confirmPasswordErrorLabel.setText("");
        }

        User user;
        if (isValid) {
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            imagePath = imagePath == null ? MainApplication.defaultImagePath : imagePath;
            Role role = switch (roleComboBox.getSelectionModel().getSelectedItem()) {
                case "Admin" -> Role.ADMIN;
                case "Librarian" -> Role.LIBRARIAN;
                default -> Role.USER;
            };
            user = new User(userName, password, fullName, role, email, phone, imagePath);
        } else {
            return;
        }


        MainApplication.userList.set(MainApplication.loggedInUserIndex, user);
        if (user.getRole().equals(Role.ADMIN)) {
            HelperFunctions.switchScene("adminDashboard");
        } else {
            HelperFunctions.switchScene("userDashboard");
        }
    }


    @FXML
    void imageViewOnClick(MouseEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
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

    @FXML
    void cancelButtonOnClick(ActionEvent actionEvent) {
        if (loggedInUser.getRole().equals(Role.ADMIN))
            HelperFunctions.switchScene("adminDashboard");
        else
            HelperFunctions.switchScene("userDashboard");
    }
}
