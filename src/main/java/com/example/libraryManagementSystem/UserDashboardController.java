package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Book;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserDashboardController implements Initializable {

    @FXML
    private ComboBox<String> bookCategoryComboBox;

    @FXML
    private ComboBox<String> bookSelectComboBox;

    @FXML
    private Label authorErrorLabel;

    @FXML
    private TextField authorField;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Label categoryErrorLabel;

    @FXML
    private TextField categoryField;

    @FXML
    private Label copiesErrorLabel;

    @FXML
    private TextField copiesField;

    @FXML
    private Label dateErrorLabel;

    @FXML
    private TextField dateField;

    @FXML
    private Label isbnErrorLabel;

    @FXML
    private TextField isbnField;

    @FXML
    private Label languageErrorLabel;

    @FXML
    private TextField languageField;

    @FXML
    private Button logoutButton;

    @FXML
    private Label pagesErrorLabel;

    @FXML
    private TextField pagesField;

    @FXML
    private Label publisherErrorLabel;

    @FXML
    private TextField publisherField;

    @FXML
    private Label titleErrorLabel;

    @FXML
    private TextField titleField;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label usernameLabel;


    private final User loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookCategoryComboBox.getItems().setAll("All");
        bookCategoryComboBox.getItems().addAll(MainApplication.categories);
        bookSelectComboBox.getItems().setAll(MainApplication.bookList
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList())
        );
        bookCategoryComboBox.setOnAction(this::onCategorySelected);


        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        usernameLabel.setText(loggedInUser.getFullName());
    }

    @FXML
    void searchButtonOnClick(ActionEvent event) {
        String selectedBook = bookSelectComboBox.getValue();
        Book book = MainApplication.bookList.stream()
                .filter(b -> b.getTitle().equals(selectedBook))
                .findFirst().orElse(null);
        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            isbnField.setText(book.getISBN());
            languageField.setText(book.getLanguage());
            publisherField.setText(book.getPublisher());
            categoryField.setText(book.getCategory());
            dateField.setText(book.getDateOfPublication());
            pagesField.setText(String.valueOf(book.getPagesNumber()));
            copiesField.setText(String.valueOf(book.getCopiesNumber()));
            bookImageView.setImage(new Image(new File(book.getImagePath()).toURI().toString()));
        }


    }

    @FXML
    void borrowButtonOnClick(ActionEvent event) {

    }

    @FXML
    void clearButtonOnClick(ActionEvent event) {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        languageField.clear();
        publisherField.clear();
        categoryField.clear();
        dateField.clear();
        pagesField.clear();
        copiesField.clear();
        bookImageView.setImage(null);
    }


    @FXML
    void smallProfileImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("profileDashboard");
    }

    @FXML
    void logoutButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");
    }

    @FXML
    void onCategorySelected(ActionEvent event) {
        String selectedCategory = bookCategoryComboBox.getValue();
        if (selectedCategory.equals("All") || selectedCategory.isEmpty()) {
            bookSelectComboBox.getItems().setAll(MainApplication.bookList
                    .stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList())
            );
        } else {
            bookSelectComboBox.getItems().setAll(MainApplication.bookList
                    .stream()
                    .filter(book -> book.getCategory().equals(selectedCategory))
                    .map(Book::getTitle)
                    .collect(Collectors.toList())
            );
        }
    }

}
