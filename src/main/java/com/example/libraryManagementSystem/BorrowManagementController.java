package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.DBCode.BookRepository;
import com.example.libraryManagementSystem.DBCode.UserRepository;
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
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BorrowManagementController implements Initializable {


    @FXML
    private TableView<BorrowManagementTable> theTableView;
    @FXML
    private TableColumn<BorrowManagementTable, Integer> bookIdColumn;
    @FXML
    private TableColumn<BorrowManagementTable, ImageView> bookImageColumn;
    @FXML
    private TableColumn<BorrowManagementTable, String> bookTitleColumn;
    @FXML
    private TableColumn<BorrowManagementTable, String> borrowStatusColumn;
    @FXML
    private TableColumn<BorrowManagementTable, Integer> userIdColumn;
    @FXML
    private TableColumn<BorrowManagementTable, ImageView> userImageColumn;
    @FXML
    private TableColumn<BorrowManagementTable, String> userNameColumn;
    @FXML
    private ComboBox<String> selectStatusComboBox;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView smallProfileImageView;

    private final User loggedInUser = HelperFunctions.getLoggedInUser();
    private final BookRepository bookRepository = new BookRepository();
    private final UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(loggedInUser.getFullName());
        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));

        selectStatusComboBox.getItems().addAll("All", "Pending", "Approved");

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userImageColumn.setCellValueFactory(new PropertyValueFactory<>("userImage"));
        userImageColumn.setCellValueFactory(cellData -> {
            String userImagePath = cellData.getValue().getUserImage();
            ImageView imageView = new ImageView(new Image(new File(userImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookImageColumn.setCellValueFactory(new PropertyValueFactory<>("bookImage"));
        bookImageColumn.setCellValueFactory(cellData -> {
            String bookImagePath = cellData.getValue().getBookImage();
            ImageView imageView = new ImageView(new Image(new File(bookImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });
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
        setTheTableView();

    }

    private void setTheTableView() {
        ObservableList<BorrowManagementTable> tableData = FXCollections.observableArrayList();
        try {
            tableData.addAll(bookRepository.getBorrowRequests());
        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Error in fetching data");
        }
        theTableView.setItems(tableData);
        theTableView.refresh();

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
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("librarianWelcome");
    }

    @FXML
    void statisticsButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminStatisticsDashboard");

    }

    @FXML
    void approveButtonOnClick(ActionEvent actionEvent) {
        BorrowManagementTable selectedRow = theTableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                bookRepository.approveBorrowRegistration(selectedRow.getUserId(), selectedRow.getBookId());
            } catch (SQLException e) {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Error in approving registration");
            }
        } else {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Please select a row to approve");
            return;
        }
        HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "Registration approved successfully");
        setTheTableView();
    }

    @FXML
    void rejectButtonOnClick(ActionEvent actionEvent) {
        BorrowManagementTable selectedRow = theTableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                bookRepository.rejectRegistration(selectedRow.getUserId(), selectedRow.getBookId());
            } catch (SQLException e) {
                HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Error in rejecting registration");
            }
        } else {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Please select a row to reject");
        }
        HelperFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "Registration rejected successfully");
        setTheTableView();


    }

    @FXML
    void selectStatusOnAction(ActionEvent actionEvent) {
        String selectedStatus = selectStatusComboBox.getSelectionModel().isEmpty() ? "All"
                : selectStatusComboBox.getSelectionModel().getSelectedItem();
        List<BorrowManagementTable> filteredTableData;
        try {
            if (selectedStatus.equals("All")) {
                filteredTableData = bookRepository.getBorrowRequests();
            } else {
                filteredTableData = bookRepository.getBorrowRequests().stream()
                        .filter(request -> request.getBorrowStatus().equals(selectedStatus))
                        .collect(Collectors.toList());

            }
            theTableView.setItems(FXCollections.observableArrayList(filteredTableData));
        } catch (SQLException e) {
            HelperFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Error in fetching data");
        }
    }
}
