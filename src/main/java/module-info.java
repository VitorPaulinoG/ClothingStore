module com.vitorpg.clothingstore {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.bouncycastle.provider;
    requires java.sql;


    opens com.vitorpg.clothingstore to javafx.fxml;
    opens com.vitorpg.clothingstore.models to javafx.base;
    exports com.vitorpg.clothingstore;
    exports com.vitorpg.clothingstore.controllers;
    exports com.vitorpg.clothingstore.models;
    opens com.vitorpg.clothingstore.controllers to javafx.fxml;
}