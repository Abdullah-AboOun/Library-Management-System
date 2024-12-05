module com.example.libraryManagementSystem {

    requires javafx.fxml;

    requires java.desktop;
    requires javafx.swing;
    requires jdk.compiler;
    requires atlantafx.base;

    opens com.example.libraryManagementSystem to javafx.fxml;

    exports com.example.libraryManagementSystem;

}
