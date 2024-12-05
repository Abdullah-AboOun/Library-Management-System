module com.example.libraryManagementSystem {

    requires javafx.fxml;

    requires java.desktop;
    requires javafx.swing;
    requires jdk.compiler;
    requires atlantafx.base;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material2;

    opens com.example.libraryManagementSystem to javafx.fxml;

    exports com.example.libraryManagementSystem;

}
