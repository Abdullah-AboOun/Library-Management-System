
module com.example.librarymanagmentssystem {

    requires javafx.controls;

    requires javafx.fxml;

    requires transitive javafx.graphics;

    opens com.example.librarymanagmentssystem to javafx.fxml;

    exports com.example.librarymanagmentssystem;

}
