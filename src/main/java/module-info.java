module com.example.libraryManagementSystem {

    requires javafx.controls;

    requires javafx.fxml;

    requires transitive javafx.graphics;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.libraryManagementSystem to javafx.fxml;

    exports com.example.libraryManagementSystem;

}
