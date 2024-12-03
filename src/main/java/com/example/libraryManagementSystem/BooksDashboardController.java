package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.Book;
import com.example.libraryManagementSystem.entity.User;
import com.sun.tools.javac.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BooksDashboardController implements Initializable {

    public ImageView smallProfileImageView;
    public Label usernameLabel;
    public ImageView bookImageView;
    public TextField titleField;
    public Label titleErrorLabel;
    public TextField authorField;
    public Label authorErrorLabel;
    public TextField isbnField;
    public Label isbnErrorLabel;
    public TextField dateField;
    public Label dateErrorLabel;
    public ComboBox languageComboBox;
    public Label languageErrorLabel;
    public ComboBox categoryComboBox;
    public Label categoryErrorLabel;
    public TextField publisherField;
    public Label publisherErrorLabel;
    public TextField pagesField;
    public Label pagesErrorLabel;
    public TextField copiesField;
    public Label copiesErrorLabel;
    public TableView<Book> bookTableView;
    public TableColumn<Book, String> titleColumn;
    public TableColumn<Book, String> authorColumn;
    public TableColumn<Book, String> dateOfPublicationColumn;
    public TableColumn<Book, String> isbnColumn;
    public TableColumn<Book, String> languageColumn;
    public TableColumn<Book, String> categoryColumn;
    public TableColumn<Book, String> publisherColumn;
    public TableColumn<Book, Integer> pagesNumberColumn;
    public TableColumn<Book, Integer> copiesNumberColumn;
    public TableColumn<Book, ImageView> bookImageColumn;
    public ComboBox bookCategoryFilterComboBox;
    User loggedInUser;
    String imagePath;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInUser = MainApplication.userList.get(MainApplication.loggedInUserIndex);
        String userImagePath = loggedInUser.getImagePath();
        smallProfileImageView.setImage(new Image(new File(userImagePath).toURI().toString()));
        usernameLabel.setText(loggedInUser.getFullName());

        bookCategoryFilterComboBox.getItems().add("All");
        bookCategoryFilterComboBox.getItems().addAll(MainApplication.categories);
        languageComboBox.getItems().addAll(MainApplication.languages);
        categoryComboBox.getItems().addAll(MainApplication.categories);

        titleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        authorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAuthor()));

        dateOfPublicationColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateOfPublication()));

        isbnColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getISBN()));

        languageColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLanguage()));

        categoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategory()));

        publisherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPublisher()));

        pagesNumberColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPagesNumber()));

        copiesNumberColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCopiesNumber()));

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

    public void bookImageViewOnClick(MouseEvent mouseEvent) {
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

    public void addButtonOnClick(ActionEvent actionEvent) {
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
            String language = languageComboBox.getSelectionModel().getSelectedItem().toString();
            String category = categoryComboBox.getSelectionModel().getSelectedItem().toString();
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
    }


    public void updateButtonOnClick(ActionEvent actionEvent) {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String date = dateField.getText();
        String language = languageComboBox.getSelectionModel().getSelectedItem().toString();
        String category = categoryComboBox.getSelectionModel().getSelectedItem().toString();
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
    }

    public void deleteButtonOnClick(ActionEvent actionEvent) {
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        if (book == null) {
            return;
        }
        MainApplication.bookList.remove(book);
    }

    public void cancelButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("dashboard");
    }

    public void smallProfileImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("profileDashboard");
    }

    public void dashboardButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("dashboard");
    }

    public void bookCategoryFilterComboBox(ActionEvent actionEvent) {
        String category = bookCategoryFilterComboBox.getSelectionModel().getSelectedItem().toString();
        if (category.equals("All")) {
            bookTableView.setItems(MainApplication.bookList);
        } else {
            bookTableView.setItems(MainApplication.bookList.filtered(book -> book.getCategory().equals(category)));
        }

    }

    public void addBookCategoryImageViewOnClick(MouseEvent mouseEvent) {
        HelperFunctions.switchScene("bookCategoryDashboard");
    }
}