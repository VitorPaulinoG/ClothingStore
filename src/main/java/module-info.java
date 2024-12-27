module com.vitorpg.clothingstore {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.swing;


    opens com.vitorpg.clothingstore to javafx.fxml;
    opens com.vitorpg.clothingstore.models to javafx.base;
    exports com.vitorpg.clothingstore;
    exports com.vitorpg.clothingstore.controllers;
    opens com.vitorpg.clothingstore.controllers to javafx.fxml;
}