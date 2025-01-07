package com.example.libraryManagementSystem;

import atlantafx.base.theme.Styles;
import com.example.libraryManagementSystem.DBCode.BookRepository;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LibrarianStatisticsController implements Initializable {
    @FXML
    private VBox mainVBox;
    @FXML
    private Label allBooksCountLabel;
    @FXML
    private Label borrowedBooksCountLabel;
    @FXML
    private Label pendingBooksCountLabel;
    @FXML
    private Label approvedBooksCountLabel;
    @FXML
    private Button logoutButton;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label usernameLabel;

    private final User loggedInUser = HelperFunctions.getLoggedInUser();

    TableView<BorrowManagementTable> tableView = new TableView<>();
    BookRepository bookRepository = new BookRepository();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(loggedInUser.getFullName());
        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));


        try {
            allBooksCountLabel.setText(String.valueOf(bookRepository.getAllBooks().size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        List<BorrowManagementTable> borrowedBooks;
        try {
            borrowedBooks = bookRepository.getBorrowRequests();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        borrowedBooksCountLabel.setText(String.valueOf(borrowedBooks.size()));
        pendingBooksCountLabel.setText(String.valueOf(borrowedBooks.stream().filter(book ->
                book.getBorrowStatus().equals("Pending")).count()));
        approvedBooksCountLabel.setText(String.valueOf(borrowedBooks.stream().filter(book ->
                book.getBorrowStatus().equals("Approved")).count()));

        tableView = new TableView<>();
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
        HelperFunctions.switchScene("borrowManagement");
    }


    @FXML
    void borrowedBooksImageOnClick(MouseEvent mouseEvent) {
        initializeTable("all");
    }

    @FXML
    void approvedBooksImageOnClick(MouseEvent mouseEvent) {
        initializeTable("Approved");
    }

    @FXML
    void pendingBooksImageOnClick(MouseEvent mouseEvent) {
        initializeTable("Pending");
    }

    private void initializeTable(String status) {
        mainVBox.getChildren().clear();

        TableColumn<BorrowManagementTable, String> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<BorrowManagementTable, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<BorrowManagementTable, ImageView> userImageColumn = new TableColumn<>("User Image");
        userImageColumn.setCellValueFactory(cellData -> {
            String userImagePath = cellData.getValue().getUserImage();
            ImageView imageView = new ImageView(new Image(new File(userImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });

        TableColumn<BorrowManagementTable, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<BorrowManagementTable, String> bookTitleColumn = new TableColumn<>("Book Title");
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        TableColumn<BorrowManagementTable, ImageView> bookImageColumn = new TableColumn<>("Book Image");
        bookImageColumn.setCellValueFactory(cellData -> {
            String bookImagePath = cellData.getValue().getBookImage();
            ImageView imageView = new ImageView(new Image(new File(bookImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });

        TableColumn<BorrowManagementTable, String> borrowStatusColumn = new TableColumn<>("Borrow Status");
        borrowStatusColumn.setCellValueFactory(new PropertyValueFactory<>("borrowStatus"));
        borrowStatusColumn.setCellFactory(column -> new TableCell<BorrowManagementTable, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    if (status.equals("Approved")) {
                        setTextFill(Color.GREEN);
                    } else if (status.equals("Pending")) {
                        setTextFill(Color.VIOLET);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });

        tableView.getColumns().addAll(userIdColumn, userNameColumn, userImageColumn,
                bookIdColumn, bookTitleColumn, bookImageColumn, borrowStatusColumn);
        ObservableList<BorrowManagementTable> tableData = FXCollections.observableArrayList();

        try {
            tableData.addAll(bookRepository.getBorrowRequests());
        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Error in fetching data");
        }
        if (status.equals("all")) {
            tableView.setItems(tableData);
        } else if (status.equals("Approved") || status.equals("Pending")) {
            tableView.setItems(tableData.filtered(book -> book.getBorrowStatus().equals(status)));
        }


        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            HelperFunctions.switchScene("librarianStatisticsDashboard");
        });
        Styles.toggleStyleClass(backButton, Styles.DANGER);

        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        Button searchButton = new Button("Search");
        searchButton.setOnAction(actionEvent -> {
            String searchText = searchField.getText().toLowerCase();
            if (searchText.isEmpty()) {
                tableView.setItems(tableData);
                return;
            }
            ObservableList<BorrowManagementTable> filteredData = tableData.filtered(book ->
                    book.getUserName().toLowerCase().contains(searchText) ||
                    book.getBookTitle().toLowerCase().contains(searchText));
            tableView.setItems(filteredData);
        });

        Styles.toggleStyleClass(searchButton, Styles.SUCCESS);
        Styles.toggleStyleClass(tableView, Styles.BORDERED);
        Styles.toggleStyleClass(tableView, Styles.STRIPED);
        HBox searchHBox = new HBox();
        searchHBox.setSpacing(20);
        searchHBox.getChildren().addAll(searchField, searchButton, backButton);


        mainVBox.getChildren().addAll(searchHBox);
        mainVBox.getChildren().add(tableView);

    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("librarianWelcome");
    }


}

