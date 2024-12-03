package com.example.libraryManagementSystem;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class BooksCategoryController {
    public TextField categoryField;

    public void addCategoryButtonOnClick(ActionEvent actionEvent) {
    }

    public void cancelCategoryButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("bookDashboard");
    }
}
