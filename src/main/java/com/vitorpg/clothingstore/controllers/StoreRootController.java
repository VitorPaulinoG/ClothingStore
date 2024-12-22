package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StoreRootController {
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnProducts;
    @FXML
    private Button btnSale;
    @FXML
    private Pane paneSubScene;

    private Button currentPageButton = null;

    @FXML
    public void initialize() {
        btnOverview.setOnAction(e -> {
            setCurrentPage(btnOverview);
            loadSubScene("overview-view");
        });

        btnProducts.setOnAction(e -> {
            setCurrentPage(btnProducts);
//            loadSubScene("");
        });
        btnSale.setOnAction(e -> {
            setCurrentPage(btnSale);
//            loadSubScene("");
        });
        btnOverview.fire();
    }

    private void setCurrentPage(Button button) {
        if (currentPageButton != null) {
            currentPageButton.getStyleClass().remove("nav-button-current");
        }

        button.getStyleClass().add("nav-button-current");
        currentPageButton = button;
    }

    private void loadSubScene(String sceneFxmlName) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(String.format("%s.fxml", sceneFxmlName)));
            Pane pane = fxmlLoader.load();
            paneSubScene.getChildren().setAll(pane);

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }



}
