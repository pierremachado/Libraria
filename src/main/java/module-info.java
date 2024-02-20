module com.uefs.libraria {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens com.uefs.libraria.model to javafx.base;
    opens com.uefs.libraria to javafx.fxml;
    exports com.uefs.libraria.model;
    exports com.uefs.libraria;
    exports com.uefs.libraria.controllers;
    opens com.uefs.libraria.controllers to javafx.fxml;
}