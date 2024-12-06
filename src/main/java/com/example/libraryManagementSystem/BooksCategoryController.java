package com.example.libraryManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BooksCategoryController {

    @FXML
    private TextField categoryField;

    @FXML
    void addCategoryButtonOnClick(ActionEvent actionEvent) {
        String category = categoryField.getText();
        if (category.isEmpty()) {
            return;
        }
        MainApplication.categories.add(category);
        HelperFunctions.switchScene("bookDashboard");
    }

    @FXML
    void cancelCategoryButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("bookDashboard");
    }
}
