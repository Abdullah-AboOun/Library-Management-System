package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.UserRepository;
import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminDashboardController implements Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    private Label confirmPasswordErrorLabel;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label TopFullnameLabel;

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

    private User loggedInUser;
    private String imagePath;
    UserRepository userRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository();
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));
        roleComboBox.getItems().addAll("ADMIN", "USER", "LIBRARIAN");
        roleFilterComboBox.getItems().addAll("All", "ADMIN", "USER", "LIBRARIAN");
        loggedInUser = HelperFunctions.getLoggedInUser();
        String imagePath = loggedInUser.getImagePath();
        profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        smallProfileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        TopFullnameLabel.setText(loggedInUser.getFullName());

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        profileImageColumn.setCellValueFactory(cellData -> {
            String userImagePath = cellData.getValue().getImagePath();
            ImageView imageView = new ImageView(new Image(new File(userImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });

        setUserTableFromDB();

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
        if (!validateInputs()) {
            return;
        }

        try {
            User user = new User(
                    userNameField.getText(),
                    passwordField.getText(),
                    fullNameField.getText(),
                    Role.valueOf(roleComboBox.getSelectionModel().getSelectedItem()),
                    emailField.getText(),
                    phoneField.getText(),
                    imagePath == null ? MainApplication.defaultImagePath : imagePath);

            if (userRepository.getUserByUsername(user.getUserName()) != null) {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "User already exists!");
                return;
            }

            if (userRepository.addUser(user)) {
                HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully!");
                clearFields();
            } else {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user!");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred!");
        }
        setUserTableFromDB();
    }

    @FXML
    void updateButtonOnClick(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }

        String finalImagPath = imagePath;
        try {
            User user = new User(
                    userNameField.getText(),
                    passwordField.getText(),
                    fullNameField.getText(),
                    Role.valueOf(roleComboBox.getSelectionModel().getSelectedItem()),
                    emailField.getText(),
                    phoneField.getText(),
                    imagePath == null ? MainApplication.defaultImagePath : imagePath);

            if (userRepository.getUserByUsername(user.getUserName()) == null) {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error",
                        "No user found with username: " + user.getUserName());
                return;
            }

            if (userRepository.updateUser(user, UserRepository.getUserIdByUsername(user.getUserName()))) {
                HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
                clearFields();
                setUserTableFromDB();

                if (userTableView.getSelectionModel().getSelectedItem().getUserName()
                        .equals(loggedInUser.getUserName())) {
                    TopFullnameLabel.setText(user.getFullName());
                    smallProfileImageView.setImage(new Image(new File(finalImagPath).toURI().toString()));

                }
            } else {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user!");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred!");
        }
        setUserTableFromDB();

    }

    @FXML
    void deleteButtonOnClick(ActionEvent actionEvent) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Please select a user to delete");
            return;
        }

        try {
            if (userRepository.deleteUser(selectedUser.getUserName())) {
                HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully!");
                setUserTableFromDB();

                if (selectedUser.getUserName().equals(loggedInUser.getUserName())) {
                    HelperFunctions.switchScene("login");
                }
            } else {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user");
            }
        } catch (SQLException e) {
            System.err.println("Database error while deleting user: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred");
        }
        setUserTableFromDB();
    }

    @FXML
    void cancelButtonOnClick(ActionEvent actionEvent) {
        clearFields();
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
        try {
            String selectedRole = roleFilterComboBox.getSelectionModel().isEmpty() ? "All"
                    : roleFilterComboBox.getSelectionModel().getSelectedItem();

            List<User> filteredUsers;
            if (selectedRole.equals("All")) {
                filteredUsers = userRepository.getAllUsers();
            } else {
                filteredUsers = userRepository.getAllUsers().stream()
                        .filter(user -> user.getRole().toString().equals(selectedRole))
                        .collect(Collectors.toList());
            }

            userTableView.setItems(FXCollections.observableArrayList(filteredUsers));

        } catch (SQLException e) {
            System.err.println("Error filtering users: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to filter users");
        }
    }

    @FXML
    void logoutButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminWelcome");
    }

    private void clearFields() {
        userNameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        fullNameField.clear();
        emailField.clear();
        phoneField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        imagePath = null;
    }

    private void setUserTableFromDB() {
        try {
            ObservableList<User> users = FXCollections.observableArrayList(userRepository.getAllUsers());
            userTableView.setItems(users);
            userTableView.refresh();
        } catch (SQLException e) {
            System.err.println("Error loading users: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load users");
        }
    }

    private boolean validateInputs() {
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

        return isValid;
    }

    @FXML
    void statisticsButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminStatisticsDashboard");
    }
}
