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
import javafx.stage.FileChooser;

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
    public Label usernameLabel;
    User loggedInUser;
    String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.getItems().addAll("Admin", "User", "Librarian");
        loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);
        String imagePath = loggedInUser.getImagePath();
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        smallProfileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        usernameLabel.setText(loggedInUser.getFullName());
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

        roleColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getRole()));

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

        userTableView.getSelectionModel().selectedItemProperty().addListener((
                observableValue, oldValue, newValue) -> {

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
                smallProfileImageView.setImage(new Image(new File(user.getImagePath()).toURI().toString()));
            }
        });

    }

    public void imageViewOnClick(MouseEvent mouseEvent) {
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

    public void addButtonOnClick(ActionEvent actionEvent) {


        Role role = switch (roleComboBox.getSelectionModel().getSelectedItem().toString()) {
            case "Admin" -> Role.ADMIN;
            case "Librarian" -> Role.Librarian;
            default -> Role.USER;
        };

        String userName = userNameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        imagePath = imagePath == null ? MainApplication.defaultImagePath : imagePath;
        User user = new User(userName, password, fullName, role, email, phone, imagePath);

        if (MainApplication.userList.contains(user)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User already exists!");
            alert.showAndWait();
            return;
        }

        MainApplication.userList.add(user);
    }

    public void updateButtonOnClick(ActionEvent actionEvent) {


        String userName = userNameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        imagePath = imagePath == null ? MainApplication.defaultImagePath : imagePath;
        Role role = switch (roleComboBox.getSelectionModel().getSelectedItem().toString()) {
            case "Admin" -> Role.ADMIN;
            case "Librarian" -> Role.Librarian;
            default -> Role.USER;
        };
        User user = new User(userName, password, fullName, role, email, phone, imagePath);

        if (!MainApplication.userList.contains(user)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No user found with user name: " + userName);
            alert.showAndWait();
            return;
        }
        int i = MainApplication.userList.indexOf(user);
        MainApplication.userList.set(i, user);
        userTableView.refresh();


//        user.setUserName(userNameField.getText());
//        user.setPassword(passwordField.getText());
//        user.setFullName(fullNameField.getText());
//        user.setEmail(emailField.getText());
//        user.setPhoneNumber(phoneField.getText());
//        user.setRole(role);
//        user.setImagePath(imagePath == null ? MainApplication.defaultImagePath : imagePath);

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

    public void smallProfileImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("profileDashboard");
    }
}
