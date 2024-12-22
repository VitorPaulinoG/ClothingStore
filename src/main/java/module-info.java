module com.vitorpg.clothingstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.vitorpg.clothingstore to javafx.fxml;
    exports com.vitorpg.clothingstore;
    exports com.vitorpg.clothingstore.controllers;
    opens com.vitorpg.clothingstore.controllers to javafx.fxml;
}