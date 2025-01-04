package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.UserRepository;
import com.example.libraryManagementSystem.entity.Role;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminStatisticsController implements Initializable {

    @FXML
    private Label allUsersCountLabel;
    @FXML
    private Label adminsCountLabel;
    @FXML
    private Label librariansCountLabel;
    @FXML
    private Label usersCountLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label usernameLabel;

    private final UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User loggedInUser = HelperFunctions.getLoggedInUser();
        usernameLabel.setText(loggedInUser.getFullName());
        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));
        List<User> allUsers = null;
        try {
            allUsers = userRepository.getAllUsers();
            allUsersCountLabel.setText(String.valueOf(allUsers.size()));

            adminsCountLabel.setText(String.valueOf(allUsers.stream().filter(user ->
                    user.getRole().equals(Role.ADMIN)).count()));
            librariansCountLabel.setText(String.valueOf(allUsers.stream().filter(user ->
                    user.getRole().equals(Role.LIBRARIAN)).count()));
            usersCountLabel.setText(String.valueOf(allUsers.stream().filter(user ->
                    user.getRole().equals(Role.USER)).count()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void bookButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("bookDashboard");
    }

    @FXML
    void logoutButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }

    @FXML
    void smallProfileImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("profileDashboard");
    }

    @FXML
    void dashboardButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminDashboard");
    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminWelcome");
    }


}
