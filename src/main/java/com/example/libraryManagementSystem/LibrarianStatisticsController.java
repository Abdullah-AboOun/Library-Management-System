package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.BookRepository;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
    }

    @FXML
    void allBooksImageOnClick(MouseEvent mouseEvent) {
    }

    @FXML
    void approvedBooksImageOnClick(MouseEvent mouseEvent) {
    }

    @FXML
    void pendingBooksImageOnClick(MouseEvent mouseEvent) {
    }

    private void initializeTable() {

    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("librarianWelcome");
    }


}

