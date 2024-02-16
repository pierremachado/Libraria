module com.uefs.libraria {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens com.uefs.libraria to javafx.fxml;
    exports com.uefs.libraria;
}