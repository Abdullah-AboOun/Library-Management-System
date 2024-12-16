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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UserWelcomeController implements Initializable {
    @FXML
    private Button logoutButton;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label usernameLabel;

    private final User loggedInUser = HelperFunctions.getLoggedInUser();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(loggedInUser.getFullName());
        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));
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
    void borrowButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("userDashboard");
    }

    @FXML
    void borrowedButtonOnClick(ActionEvent actionEvent) {

    }
}

