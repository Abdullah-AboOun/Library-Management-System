package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label confirmPasswordErrorLabel;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label usernameLabel;

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
    private ComboBox<String> roleFilterComboBox;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label userNameErrorLabel;

    @FXML
    private TextField userNameField;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> fullNameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableColumn<User, ImageView> profileImageColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    User loggedInUser;
    String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.getItems().addAll("ADMIN", "USER", "LIBRARIAN");
        roleFilterComboBox.getItems().addAll("All", "ADMIN", "USER", "LIBRARIAN");
        loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);
        String imagePath = loggedInUser.getImagePath();
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        smallProfileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        usernameLabel.setText(loggedInUser.getFullName());
        if (loggedInUser.getRole() == Role.USER) {
            roleComboBox.setDisable(true);
        } else if (loggedInUser.getRole() == Role.LIBRARIAN) {
            roleComboBox.getItems().remove("ADMIN");
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
                new SimpleObjectProperty<>(cellData.getValue().getRole().toString()));

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
                int roleIndex = roleComboBox.getItems().indexOf(user.getRole().toString());
                roleComboBox.getSelectionModel().select(roleIndex);
                passwordField.setText(user.getPassword());
                confirmPasswordField.setText(user.getPassword());
                profileImageView.setImage(new Image(new File(user.getImagePath()).toURI().toString()));
            }
        });

    }

    @FXML
    void imageViewOnClick(MouseEvent mouseEvent) {
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

    @FXML
    void addButtonOnClick(ActionEvent actionEvent) {

        boolean isValid = true;
        if (roleComboBox.getSelectionModel().isEmpty()) {
            roleErrorLabel.setText("Role is required!");
            isValid = false;
        } else {
            roleErrorLabel.setText("");
        }
        if (userNameField.getText().isEmpty()) {
            userNameErrorLabel.setText("Username is required!");
            isValid = false;
        } else {
            userNameErrorLabel.setText("");
        }
        if (passwordField.getText().isEmpty()) {
            passwordErrorLabel.setText("Password is required!");
            isValid = false;
        } else {
            passwordErrorLabel.setText("");
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            confirmPasswordErrorLabel.setText("Passwords do not match!");
            isValid = false;
        } else {
            confirmPasswordErrorLabel.setText("");
        }
        if (fullNameField.getText().isEmpty()) {
            fullNameErrorLabel.setText("Full name is required!");
            isValid = false;
        } else {
            fullNameErrorLabel.setText("");
        }
        if (emailField.getText().isEmpty()) {
            emailErrorLabel.setText("Email is required!");
            isValid = false;
        } else {
            emailErrorLabel.setText("");
        }
        if (phoneField.getText().isEmpty()) {
            phoneErrorLabel.setText("Phone is required!");
            isValid = false;
        } else {
            phoneErrorLabel.setText("");
        }

        if (!isValid) {
            return;
        }

        Role role = switch (roleComboBox.getSelectionModel().getSelectedItem().toString()) {
            case "ADMIN" -> Role.ADMIN;
            case "LIBRARIAN" -> Role.LIBRARIAN;
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

    @FXML
    void updateButtonOnClick(ActionEvent actionEvent) {

        String userName = userNameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        imagePath = imagePath == null ? MainApplication.defaultImagePath : imagePath;
        Role role = switch (roleComboBox.getSelectionModel().getSelectedItem().toString()) {
            case "ADMIN" -> Role.ADMIN;
            case "LIBRARIAN" -> Role.LIBRARIAN;
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
        if (i == MainApplication.loggedInUserIndex) {
            usernameLabel.setText(user.getFullName());
        }
    }

    @FXML
    void deleteButtonOnClick(ActionEvent actionEvent) {
        User user = userTableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            return;
        }
        MainApplication.userList.remove(user);
        MainApplication.loggedInUserIndex = MainApplication.userList.indexOf(loggedInUser);
    }

    @FXML
    void cancelButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }

    @FXML
    void smallProfileImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("profileDashboard");
    }

    @FXML
    void bookButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("bookDashboard");
    }

    @FXML
    void roleFilterComboBox(ActionEvent actionEvent) {
        String role = roleFilterComboBox.getSelectionModel().isEmpty() ? "All"
                : roleFilterComboBox.getSelectionModel().getSelectedItem().toString();

        if (role.equals("All")) {
            userTableView.setItems(MainApplication.userList);
            return;
        }
        userTableView.setItems(MainApplication.userList.filtered(user -> user.getRole().toString().equals(role)));

    }
}
