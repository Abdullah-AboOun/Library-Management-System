package com.example.libraryManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public ImageView profileImageView;
    public TextField userNameField;
    public Label userNameErrorLabel;
    public TextField fullNameField;
    public Label fullNameErrorLabel;
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
    public ImageView smallProfileImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.getItems().addAll("Admin", "User", "Librarian");

        String imagePath = MainApplication.loggedInUser.getImagePath();
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        smallProfileImageView.setImage(new Image(new File(imagePath).toURI().toString()));

        userNameField.setText(MainApplication.loggedInUser.getUserName());
        fullNameField.setText(MainApplication.loggedInUser.getFullName());
        emailField.setText(MainApplication.loggedInUser.getEmail());
        phoneField.setText(MainApplication.loggedInUser.getPhoneNumber());
        roleComboBox.setValue(MainApplication.loggedInUser.getRole().toString());
        passwordField.setText(MainApplication.loggedInUser.getPassword());
        confirmPasswordField.setText(MainApplication.loggedInUser.getPassword());


    }

    public void imageViewOnClick(MouseEvent mouseEvent) {

    }

    public void addButtonOnClick(ActionEvent actionEvent) {
    }

    public void updateButtonOnClick(ActionEvent actionEvent) {
    }

    public void deleteButtonOnClick(ActionEvent actionEvent) {
    }

    public void cancelButtonOnClick(ActionEvent actionEvent) {
    }
}
