package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Book;
import com.example.libraryManagementSystem.entity.User;
import javafx.beans.property.SimpleObjectProperty;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));

        loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);
        String userImagePath = loggedInUser.getImagePath();
        smallProfileImageView.setImage(new Image(new File(userImagePath).toURI().toString()));
        usernameLabel.setText(loggedInUser.getFullName());

        bookCategoryFilterComboBox.getItems().add("All");
        bookCategoryFilterComboBox.getItems().addAll(MainApplication.categories);
        languageComboBox.getItems().addAll(MainApplication.languages);
        categoryComboBox.getItems().addAll(MainApplication.categories);

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
        bookTableView.setItems(MainApplication.bookList);

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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        String projectPath = System.getProperty("user.dir");
        File initialDirectory = new File(projectPath + "/src/main/resources/com/example/libraryManagementSystem/images");
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
        }
        bookImageView.setImage(new Image(new File(imagePath).toURI().toString()));
    }

    @FXML
    void addButtonOnClick(ActionEvent actionEvent) {
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


        if (languageComboBox.getSelectionModel().isEmpty()) {
            languageErrorLabel.setText("Language is required");
            isValid = false;
        } else {
            languageErrorLabel.setText("");
        }

        if (categoryComboBox.getSelectionModel().isEmpty()) {
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

        Book book;


        if (isValid) {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String date = dateField.getText();
            String language = languageComboBox.getSelectionModel().getSelectedItem();
            String category = categoryComboBox.getSelectionModel().getSelectedItem();
            String publisher = publisherField.getText();
            int pages = Integer.parseInt(pagesField.getText());
            int copies = Integer.parseInt(copiesField.getText());
            imagePath = imagePath == null ? MainApplication.defaultBookImagePath : imagePath;
            book = new Book(title, author, date, isbn, language, category, publisher, imagePath, pages, copies);
        } else {
            return;
        }

        if (MainApplication.bookList.contains(book)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Book already exists!");
            alert.showAndWait();
            return;
        }

        MainApplication.bookList.add(book);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Book added successfully!");
        alert.showAndWait();

    }


    @FXML
    void updateButtonOnClick(ActionEvent actionEvent) {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String date = dateField.getText();
        String language = languageComboBox.getSelectionModel().getSelectedItem();
        String category = categoryComboBox.getSelectionModel().getSelectedItem();
        String publisher = publisherField.getText();
        int pages = Integer.parseInt(pagesField.getText());
        int copies = Integer.parseInt(copiesField.getText());
        imagePath = imagePath == null ? MainApplication.defaultBookImagePath : imagePath;
        Book book = new Book(title, author, date, isbn, language, category, publisher, imagePath, pages, copies);

        if (!MainApplication.bookList.contains(book)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No book found with ISBN: " + isbn);
            alert.showAndWait();
            return;
        }
        int i = MainApplication.bookList.indexOf(book);
        MainApplication.bookList.set(i, book);
        bookTableView.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Book updated successfully!");
        alert.showAndWait();

    }

    @FXML
    void deleteButtonOnClick(ActionEvent actionEvent) {
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        if (book == null) {
            return;
        }
        MainApplication.bookList.remove(book);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Book deleted successfully!");
        alert.showAndWait();
    }

    @FXML
    void cancelButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminDashboard");
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
    void bookCategoryFilterComboBox(ActionEvent actionEvent) {
        String category = bookCategoryFilterComboBox.getSelectionModel().getSelectedItem();
        if (category.equals("All")) {
            bookTableView.setItems(MainApplication.bookList);
        } else {
            bookTableView.setItems(MainApplication.bookList.filtered(book -> book.getCategory().equals(category)));
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
}