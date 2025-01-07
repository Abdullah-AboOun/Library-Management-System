package com.example.libraryManagementSystem;

import atlantafx.base.theme.Styles;
import com.example.libraryManagementSystem.DBCode.UserRepository;
import com.example.libraryManagementSystem.entity.Role;
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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminStatisticsController implements Initializable {

    @FXML
    private VBox mainVBox;
    @FXML
    private Label allUsersCountLabel;
    @FXML
    private Label adminsCountLabel;
    @FXML
    private Label librariansCountLabel;
    @FXML
    private Label usersCountLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView smallProfileImageView;

    @FXML
    private Label usernameLabel;

    private final UserRepository userRepository = new UserRepository();

    TableView<User> tableView = new TableView<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User loggedInUser = HelperFunctions.getLoggedInUser();
        usernameLabel.setText(loggedInUser.getFullName());
        smallProfileImageView.setImage(new Image(new File(loggedInUser.getImagePath()).toURI().toString()));
        logoutButton.setGraphic(new FontIcon(Material2AL.LOG_OUT));
        List<User> allUsers = null;
        try {
            allUsers = userRepository.getAllUsers();
            allUsersCountLabel.setText(String.valueOf(allUsers.size()));

            adminsCountLabel.setText(String.valueOf(allUsers.stream().filter(user ->
                    user.getRole().equals(Role.ADMIN)).count()));
            librariansCountLabel.setText(String.valueOf(allUsers.stream().filter(user ->
                    user.getRole().equals(Role.LIBRARIAN)).count()));
            usersCountLabel.setText(String.valueOf(allUsers.stream().filter(user ->
                    user.getRole().equals(Role.USER)).count()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableView = new TableView<>();

    }

    @FXML
    void bookButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("bookDashboard");
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
    void dashboardButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminDashboard");
    }

    @FXML
    void welcomeButtonOnClick(ActionEvent actionEvent) {
        HelperFunctions.switchScene("adminWelcome");
    }

    @FXML
    void allUsersImageOnClick(MouseEvent mouseEvent) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            users.addAll(userRepository.getAllUsers());
            tableView.setItems(users);
            initializeTable("all");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void adminsImageOnClick(MouseEvent mouseEvent) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            users.addAll(userRepository.getUsersByRole(Role.ADMIN));
            tableView.setItems(users);
            initializeTable("ADMIN");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void librariansImageOnClick(MouseEvent mouseEvent) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            users.addAll(userRepository.getUsersByRole(Role.LIBRARIAN));
            tableView.setItems(users);
            initializeTable("LIBRARIAN");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void usersImageOnClick(MouseEvent mouseEvent) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            users.addAll(userRepository.getUsersByRole(Role.USER));
            tableView.setItems(users);
            initializeTable("USER");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void initializeTable(String role) {
        mainVBox.getChildren().clear();

        TableColumn<User, ImageView> profileImageColumn = new TableColumn<>("Profile Image");
        profileImageColumn.setCellValueFactory(cellData -> {
            String userImagePath = cellData.getValue().getImagePath();
            ImageView imageView = new ImageView(new Image(new File(userImagePath).toURI().toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return new SimpleObjectProperty<>(imageView);
        });

        TableColumn<User, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<User, String> password = new TableColumn<>("Password");
        password.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<User, String> lastNameColumn = new TableColumn<>("FullName");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<User, Role> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));


        tableView.getColumns().addAll(profileImageColumn, username, password, lastNameColumn,
                emailColumn, phoneColumn, roleColumn);

        Styles.toggleStyleClass(tableView, Styles.BORDERED);
        Styles.toggleStyleClass(tableView, Styles.STRIPED);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            HelperFunctions.switchScene("adminStatisticsDashboard");
        });
        Styles.toggleStyleClass(backButton, Styles.DANGER);

        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        Button searchButton = new Button("Search");
        Styles.toggleStyleClass(searchButton, Styles.SUCCESS);
        searchButton.setOnAction(event -> {
            ObservableList<User> users = FXCollections.observableArrayList();
            try {
                if (searchField.getText().isEmpty()) {
                    if (role.equals("all")) {
                        users.addAll(userRepository.getAllUsers());
                        tableView.setItems(users);
                        return;
                    }
                    users.addAll(userRepository.getAllUsers()
                            .stream()
                            .filter(user -> user.getRole().equals(Role.valueOf(role)))
                            .toList());
                    tableView.setItems(users);
                    return;
                }
                users.addAll(userRepository.getUserByUsername(searchField.getText()));
                tableView.setItems(users);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        HBox searchHBox = new HBox();
        searchHBox.setSpacing(20);


        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> {
            searchField.clear();
            ObservableList<User> users = FXCollections.observableArrayList();
            try {
                if (role.equals("all")) {
                    users.addAll(userRepository.getAllUsers());
                    tableView.setItems(users);
                    return;
                }
                users.addAll(userRepository.getAllUsers()
                        .stream()
                        .filter(user -> user.getRole().equals(Role.valueOf(role)))
                        .toList());
                tableView.setItems(users);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        Styles.toggleStyleClass(clearButton, Styles.ACCENT);
        searchHBox.getChildren().addAll(searchField, searchButton, clearButton, backButton);


        mainVBox.getChildren().addAll(searchHBox);
        mainVBox.getChildren().add(tableView);
    }
}
