
module com.example.libraryManagementSystem {

    requires javafx.controls;

    requires javafx.fxml;

    requires transitive javafx.graphics;

    opens com.example.libraryManagementSystem to javafx.fxml;

    exports com.example.libraryManagementSystem;

}
