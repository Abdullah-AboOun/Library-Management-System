package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button logoutButton;

    private final User loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        usernameLabel.setText(loggedInUser.getFullName());
    }

    @FXML
    void smallProfileImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("profileDashboard");
    }

    @FXML
    void logoutButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }

}
