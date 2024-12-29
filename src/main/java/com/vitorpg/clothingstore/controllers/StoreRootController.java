package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.App;
import com.vitorpg.clothingstore.events.ChangeSubSceneEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.EventListener;

public class StoreRootController {

    @FXML
    private Button btnOverview;
    @FXML
    private Button btnProducts;
    @FXML
    private Button btnSale;
    @FXML
    private ScrollPane paneSubScene;

    private String currentPage = null;
    private Button currentPageButton = null;

    @FXML
    public void initialize() {
        paneSubScene.addEventHandler(ChangeSubSceneEvent.SUBSCENE_CHANGED, event -> {
            loadSubScene(event.getSceneFxmlName());
        });

        btnOverview.setOnAction(event -> {
            setCurrentPage(btnOverview);
            loadSubScene("overview-view");
        });

        btnProducts.setOnAction(event -> {
            setCurrentPage(btnProducts);
            loadSubScene("product-list-view");
        });
        btnSale.setOnAction(event -> {
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
        if(currentPage != null && currentPage.equals(sceneFxmlName))
            return;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(String.format("%s.fxml", sceneFxmlName)));
            Pane pane = fxmlLoader.load();
            pane.prefWidthProperty().bind(paneSubScene.widthProperty());
            pane.prefHeightProperty().bind(paneSubScene.heightProperty());
            paneSubScene.setContent(pane);
            currentPage = sceneFxmlName;
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
}
