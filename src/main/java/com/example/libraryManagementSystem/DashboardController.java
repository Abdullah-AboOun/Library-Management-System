package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public TableView<User> userTableView;
    public TableColumn<User, String> usernameColumn;
    public TableColumn<User, String> fullNameColumn;
    public TableColumn<User, String> emailColumn;
    public TableColumn<User, String> phoneColumn;
    public TableColumn<User, Role> roleColumn;
    public TableColumn<User, String> passwordColumn;
    public TableColumn<User, ImageView> profileImageColumn;
    User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.getItems().addAll("Admin", "User", "Librarian");
        loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);
        String imagePath = loggedInUser.getImagePath();
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        smallProfileImageView.setImage(new Image(new File(imagePath).toURI().toString()));

        if (loggedInUser.getRole() == Role.USER) {
            roleComboBox.setDisable(true);
        } else if (loggedInUser.getRole() == Role.Librarian) {
            roleComboBox.getItems().remove("Admin");
        }
        usernameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUserName()));

        fullNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFullName()));

        emailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));

        phoneColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        roleColumn.setCellValueFactory(cellData
                -> new SimpleObjectProperty<>(cellData.getValue().getRole()));

        passwordColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPassword()));

        profileImageColumn.setCellValueFactory(cellData -> {
            String userImagePath = cellData.getValue().getImagePath();
            ImageView imageView = new ImageView(new Image(new File(userImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });

        userTableView.setItems(MainApplication.userList);

        userTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                User user = userTableView.getSelectionModel().getSelectedItem();
                userNameField.setText(user.getUserName());
                fullNameField.setText(user.getFullName());
                emailField.setText(user.getEmail());
                phoneField.setText(user.getPhoneNumber());
                roleComboBox.setValue(user.getRole());
                passwordField.setText(user.getPassword());
                confirmPasswordField.setText(user.getPassword());
                profileImageView.setImage(new Image(new File(user.getImagePath()).toURI().toString()));
            }
        });

    }

    public void imageViewOnClick(MouseEvent mouseEvent) {

    }

    public void addButtonOnClick(ActionEvent actionEvent) {
    }

    public void updateButtonOnClick(ActionEvent actionEvent) {

    }

    public void deleteButtonOnClick(ActionEvent actionEvent) {
        User user = userTableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            return;
        }
        MainApplication.userList.remove(user);
    }

    public void cancelButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }
}
