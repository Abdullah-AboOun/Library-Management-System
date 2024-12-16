package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.BookRepository;
import com.example.libraryManagementSystem.entity.Book;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
import java.util.stream.Collectors;

public class UserDashboardController implements Initializable {

    @FXML
    private Button borrowButton;

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

    private final User loggedInUser = HelperFunctions.getLoggedInUser();
    BookRepository bookRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookRepository = new BookRepository();
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));

        borrowButton.setPrefWidth(140);
        try {
            List<String> categories = bookRepository.getCategories();
            bookCategoryComboBox.getItems().setAll("All");
            bookCategoryComboBox.getItems().addAll(categories);

            List<Book> books = bookRepository.getAllBooks();
            List<String> bookTitles = books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
            bookSelectComboBox.getItems().setAll(bookTitles);

            bookCategoryComboBox.setOnAction(this::onCategorySelected);

            // Set user profile
            smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
            usernameLabel.setText(loggedInUser.getFullName());

        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Failed to load data",
                    " Failed to load data from the database");
        }
    }

    @FXML
    void searchButtonOnClick(ActionEvent event) {
        String selectedBook = bookSelectComboBox.getValue();
        try {
            Book book = bookRepository.getAllBooks().stream()
                    .filter(b -> b.getTitle().equals(selectedBook))
                    .findFirst()
                    .orElse(null);

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
                borrowButton.setStyle("-fx-background-color: #2BB740FF; -fx-text-fill: #FFFFFFFF");
                Boolean isApproved = bookRepository.getBorrowStatusForUser(book.getISBN(), loggedInUser.getUserName());
                if (isApproved != null) {
                    if (isApproved) {
                        borrowButton.setText("Already Borrowed");
                        borrowButton.setDisable(true);
                        borrowButton.setStyle("-fx-background-color: #0A84FFFF; -fx-text-fill: #FFFFFFFF");
                    } else {
                        borrowButton.setText("Pending Approval");
                        borrowButton.setDisable(true);
                        borrowButton.setStyle("-fx-background-color: #D98709FF; -fx-text-fill: #FFFFFFFF");
                    }
                } else {
                    borrowButton.setText("Borrow");
                    borrowButton.setDisable(false);
                }

            } else {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Book not found",
                        "The book you are looking for is not found");
            }
        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR,
                    "Failed to search book", e.getMessage());
        }

    }

    @FXML
    void borrowButtonOnClick(ActionEvent event) {
        String selectedBookISBN = isbnField.getText();

        try {
            bookRepository.addBorrowRegistration(selectedBookISBN, loggedInUser.getUserName());
        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR,
                    "Failed to borrow book", e.getMessage());
        }
        HelperFunctions.showAlert(Alert.AlertType.INFORMATION,
                "Book Borrowed", "Book borrowed successfully \nPending approval...");
        borrowButton.setText("Pending Approval");
        borrowButton.setDisable(true);
        borrowButton.setStyle("-fx-background-color: #D98709FF; -fx-text-fill: #FFFFFFFF");

    }

    @FXML
    void clearButtonOnClick(ActionEvent event) {
        HelperFunctions.switchScene("userDashboard");
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
        try {
            List<Book> books;
            if (selectedCategory.equals("All") || selectedCategory.isEmpty()) {
                books = bookRepository.getAllBooks();
            } else {
                books = bookRepository.getBooksByCategory(selectedCategory);
            }

            bookSelectComboBox.getItems().setAll(
                    books.stream()
                            .map(Book::getTitle)
                            .collect(Collectors.toList()));
        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR,
                    "Failed to load books", e.getMessage());
        }
    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("userWelcome");
    }

    @FXML
    void borrowedButtonOnClick(ActionEvent actionEvent) {

    }
}
