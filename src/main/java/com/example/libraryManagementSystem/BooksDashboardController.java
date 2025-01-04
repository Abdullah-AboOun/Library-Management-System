package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.BookRepository;
import com.example.libraryManagementSystem.entity.Book;
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

public class BooksDashboardController implements Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    private Label authorErrorLabel;

    @FXML
    private TextField authorField;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Label categoryErrorLabel;

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
    private Label pagesErrorLabel;

    @FXML
    private TextField pagesField;

    @FXML
    private Label publisherErrorLabel;

    @FXML
    private TextField publisherField;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label titleErrorLabel;

    @FXML
    private TextField titleField;

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, String> languageColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, Integer> pagesNumberColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private TableColumn<Book, String> dateOfPublicationColumn;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, Integer> copiesNumberColumn;

    @FXML
    private TableColumn<Book, String> categoryColumn;

    @FXML
    private TableColumn<Book, ImageView> bookImageColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> bookCategoryFilterComboBox;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Label usernameLabel;

    User loggedInUser;
    String imagePath;
    private BookRepository bookRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));
        bookRepository = new BookRepository();

        loggedInUser = HelperFunctions.getLoggedInUser();
        String userImagePath = loggedInUser.getImagePath();
        smallProfileImageView.setImage(new Image(new File(userImagePath).toURI().toString()));
        usernameLabel.setText(loggedInUser.getFullName());

        List<String> categories = null;
        List<String> languages = null;
        try {
            categories = bookRepository.getCategories();
            languages = bookRepository.getLanguages();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Failed to load categories and languages");
            alert.showAndWait();
        }

        bookCategoryFilterComboBox.getItems().clear();
        bookCategoryFilterComboBox.getItems().add("All");
        bookCategoryFilterComboBox.getItems().addAll(categories);

        languageComboBox.getItems().clear();
        languageComboBox.getItems().addAll(languages);
        categoryComboBox.getItems().clear();
        categoryComboBox.getItems().addAll(categories);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateOfPublicationColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfPublication"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        pagesNumberColumn.setCellValueFactory(new PropertyValueFactory<>("pagesNumber"));
        copiesNumberColumn.setCellValueFactory(new PropertyValueFactory<>("copiesNumber"));

        bookImageColumn.setCellValueFactory(cellData -> {
            String bookImagePath = cellData.getValue().getImagePath();
            ImageView imageView = new ImageView(new Image(new File(bookImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });
        setBookTableFromDB();
        bookTableView.getSelectionModel().selectedItemProperty().addListener((
                observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Book book = bookTableView.getSelectionModel().getSelectedItem();
                titleField.setText(book.getTitle());
                authorField.setText(book.getAuthor());
                isbnField.setText(book.getISBN());
                dateField.setText(book.getDateOfPublication());
                languageComboBox.setValue(book.getLanguage());
                categoryComboBox.setValue(book.getCategory());
                publisherField.setText(book.getPublisher());
                pagesField.setText(String.valueOf(book.getPagesNumber()));
                copiesField.setText(String.valueOf(book.getCopiesNumber()));
                bookImageView.setImage(new Image(new File(book.getImagePath()).toURI().toString()));
            }
        });

    }

    @FXML
    void bookImageViewOnClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        String projectPath = System.getProperty("user.dir");
        File initialDirectory = new File(projectPath +
                                         "/src/main/resources/com/example/libraryManagementSystem/images");
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
        }
        bookImageView.setImage(new Image(new File(imagePath).toURI().toString()));
    }

    @FXML
    void addButtonOnClick(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }

        try {
            Book book = createBookFromFields();

            if (bookRepository.getBookByISBN(book.getISBN()) != null) {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Book already exists!");
                return;
            }

            if (bookRepository.addBook(book)) {
                HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
                clearFields();
            } else {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add book!");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred!");
        }
        setBookTableFromDB();
    }

    @FXML
    void updateButtonOnClick(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }

        try {
            Book book = createBookFromFields();

            if (bookRepository.getBookByISBN(book.getISBN()) == null) {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error",
                        "No book found with ISBN: " + book.getISBN());
                return;
            }

            if (bookRepository.updateBook(book)) {
                HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "Book updated successfully!");
                clearFields();
            } else {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update book!");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred!");
        }
        setBookTableFromDB();
    }

    @FXML
    void deleteButtonOnClick(ActionEvent actionEvent) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Please select a book to delete");
            return;
        }

        try {
            if (bookRepository.deleteBook(selectedBook.getISBN())) {
                HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully!");
                clearFields();
            } else {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete book");
            }
        } catch (SQLException e) {
            System.err.println("Database error while deleting book: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred");
        }
        setBookTableFromDB();
    }

    private Book createBookFromFields() {
        return new Book(
                titleField.getText(),
                authorField.getText(),
                dateField.getText(),
                isbnField.getText(),
                languageComboBox.getValue(),
                categoryComboBox.getValue(),
                publisherField.getText(),
                imagePath == null ? MainApplication.defaultBookImagePath : imagePath,
                Integer.parseInt(pagesField.getText()),
                Integer.parseInt(copiesField.getText()));
    }

    private void clearFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        dateField.clear();
        languageComboBox.getSelectionModel().clearSelection();
        categoryComboBox.getSelectionModel().clearSelection();
        publisherField.clear();
        pagesField.clear();
        copiesField.clear();
        bookImageView.setImage(new Image(new File(MainApplication.defaultBookImagePath).toURI().toString()));
        imagePath = null;
    }

    @FXML
    void bookCategoryFilterComboBox(ActionEvent actionEvent) {
        try {
            String selectedCategory = bookCategoryFilterComboBox.getValue();
            List<Book> books;

            if (selectedCategory == null || selectedCategory.equals("All")) {
                books = bookRepository.getAllBooks();
            } else {
                books = bookRepository.getBooksByCategory(selectedCategory);
            }

            bookTableView.setItems(FXCollections.observableArrayList(books));
        } catch (SQLException e) {
            System.err.println("Error filtering books: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to filter books");
        }
    }

    @FXML
    void addBookCategoryImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("bookCategoryDashboard");
    }

    @FXML
    void logoutButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("login");

    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminWelcome");
    }

    private void setBookTableFromDB() {
        try {
            ObservableList<Book> books = FXCollections.observableArrayList(bookRepository.getAllBooks());
            bookTableView.setItems(books);
            bookTableView.refresh();
        } catch (SQLException e) {
            System.err.println("Error loading books: " + e.getMessage());
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load books");
        }
    }

    @FXML
    void smallProfileImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("profileDashboard");
    }

    @FXML
    void cancelButtonOnClick(ActionEvent actionEvent) {
        clearFields();
    }

    @FXML
    void dashboardButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminDashboard");
    }

    private boolean validateInputs() {
        boolean isValid = true;
        if (titleField.getText().isEmpty()) {
            titleErrorLabel.setText("Title is required");
            isValid = false;
        } else {
            titleErrorLabel.setText("");
        }

        if (authorField.getText().isEmpty()) {
            authorErrorLabel.setText("Author is required");
            isValid = false;
        } else {
            authorErrorLabel.setText("");
        }

        if (isbnField.getText().isEmpty()) {
            isbnErrorLabel.setText("ISBN is required");
            isValid = false;
        } else {
            isbnErrorLabel.setText("");
        }

        if (dateField.getText().isEmpty()) {
            dateErrorLabel.setText("Date is required");
            isValid = false;
        } else {
            dateErrorLabel.setText("");
        }

        if (languageComboBox.getValue() == null) {
            languageErrorLabel.setText("Language is required");
            isValid = false;
        } else {
            languageErrorLabel.setText("");
        }

        if (categoryComboBox.getValue() == null) {
            categoryErrorLabel.setText("Category is required");
            isValid = false;
        } else {
            categoryErrorLabel.setText("");
        }

        if (publisherField.getText().isEmpty()) {
            publisherErrorLabel.setText("Publisher is required");
            isValid = false;
        } else {
            publisherErrorLabel.setText("");
        }

        if (pagesField.getText().isEmpty()) {
            pagesErrorLabel.setText("Pages is required");
            isValid = false;
        } else {
            pagesErrorLabel.setText("");
        }

        if (copiesField.getText().isEmpty()) {
            copiesErrorLabel.setText("Copies is required");
            isValid = false;
        } else {
            copiesErrorLabel.setText("");
        }

        return isValid;
    }

    @FXML
    void statisticsButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminStatisticsDashboard");
    }
}
