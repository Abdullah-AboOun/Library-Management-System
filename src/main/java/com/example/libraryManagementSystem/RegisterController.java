package com.example.libraryManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public void registerButtonOnClick(ActionEvent actionEvent) {

    }

    public void switchToLogin(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private String imagePath = "";
    public void imageViewOnClick(MouseEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        String projectPath = System.getProperty("user.dir");
        File initialDirectory = new File(projectPath + "/src/main/resources/com/example/libraryManagementSystem/images/users images");
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
        }
    }

}
