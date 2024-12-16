package com.example.libraryManagementSystem;

import java.sql.SQLException;

import com.example.libraryManagementSystem.DBCode.BookRepository;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class BooksCategoryController {

    @FXML
    private TextField categoryField;

    @FXML
    void addCategoryButtonOnClick(ActionEvent actionEvent) {
        BookRepository bookRepository = new BookRepository();
        String category = categoryField.getText().trim();
        if (category.isEmpty()) {
            return;
        }

        try {
            if (bookRepository.addCategory(category)) {
                categoryField.clear();
                HelperFunctions.switchScene("bookDashboard");
            }
        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add category");
        }
    }

    @FXML
    void cancelCategoryButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("bookDashboard");
    }
}
