package com.example.libraryManagementSystem;

import atlantafx.base.theme.Styles;
import com.example.libraryManagementSystem.DBCode.BookRepository;
import com.example.libraryManagementSystem.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserBorrowedController implements Initializable {
    @FXML
    private FlowPane mainFlowPane;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label usernameLabel;

    private final User loggedInUser = HelperFunctions.getLoggedInUser();

    BookRepository bookRepository = new BookRepository();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(loggedInUser.getFullName());
        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));
        initializeMainHBox();

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
        HelperFunctions.switchScene("userDashboard");
    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("userWelcome");
    }

    void initializeMainHBox() {
        List<BorrowManagementTable> borrowedBooks;
        mainFlowPane.getChildren().clear();
        try {
            borrowedBooks =
                    bookRepository.getBorrowedBooksByUserId(MainApplication.loggedInUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        borrowedBooks.forEach(borrowedBook -> {
            VBox vBox = new VBox();
            ImageView imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setImage(new Image(new File(borrowedBook.getBookImage()).toURI().toString()));
            Label bookTitle = new Label(borrowedBook.getBookTitle());
            Label bookStatus = new Label("Status: " + borrowedBook.getBorrowStatus());
            Button b;
            if (borrowedBook.getBorrowStatus().equals("Pending")) {
                b = new Button("Pending");
                b.setDisable(true);

            } else {
                b = new Button("Return");
                Styles.toggleStyleClass(b, Styles.ACCENT);
            }
            b.setOnAction(actionEvent -> {
                try {
                    bookRepository.returnBook(borrowedBook.getBookId(), MainApplication.loggedInUserId);
                    HelperFunctions.switchScene("userBorrowed");
                    System.out.println("Book returned successfully: " + borrowedBook.getBookTitle());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            vBox.getChildren().addAll(imageView, bookTitle, bookStatus, b);
            vBox.setSpacing(10);
            vBox.setStyle("-fx-border-color: -color-accent-emphasis; -fx-border-width: 3px; -fx-padding: 10px;");
            vBox.setAlignment(javafx.geometry.Pos.CENTER);
            mainFlowPane.getChildren().add(vBox);

        });
    }

}

