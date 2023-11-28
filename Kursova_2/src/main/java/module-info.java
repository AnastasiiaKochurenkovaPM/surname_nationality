module com.example.kursova_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jython.standalone;

    opens com.example.kursova_2 to javafx.fxml;
    exports com.example.kursova_2;
}