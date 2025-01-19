package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.App;
import com.vitorpg.clothingstore.events.ChangeSubSceneEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
    @FXML
    private StackPane spHome;

    private String currentPage = null;
    private Button currentPageButton = null;

    @FXML
    public void initialize() {
        configureEventHandlers();

        btnOverview.fire();

        configureTooltips();
    }

    private void configureEventHandlers() {
        paneSubScene.addEventHandler(ChangeSubSceneEvent.SUBSCENE_CHANGED, event -> {
            loadSubScene(event.getSceneFxmlName());
        });
        paneSubScene.addEventHandler(ChangeSubSceneEvent.SUBSCENE_CHANGED_AND_COMMUNICATION, event -> {
            loadSubScene(event.getSceneFxmlName(), event.getController());
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
            loadSubScene("sale-list-view");
        });
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

    private void loadSubScene(String sceneFxmlName, Object controller) {
        if(currentPage != null && currentPage.equals(sceneFxmlName))
            return;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(String.format("%s.fxml", sceneFxmlName)));
            fxmlLoader.setController(controller);
            Pane pane = fxmlLoader.load();
            pane.prefWidthProperty().bind(paneSubScene.widthProperty());
            pane.prefHeightProperty().bind(paneSubScene.heightProperty());
            paneSubScene.setContent(pane);
            currentPage = sceneFxmlName;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void configureTooltips() {
        Tooltip overviewTooltip = new Tooltip("Visão Geral");
        Tooltip.install(btnOverview, overviewTooltip);

        Tooltip productsTooltip = new Tooltip("Lista de Produtos");
        Tooltip.install(btnProducts, productsTooltip);

        Tooltip saleTooltip = new Tooltip("Lista de Vendas");
        Tooltip.install(btnSale, saleTooltip);

        Tooltip homeTooltip = new Tooltip("Tela inicial");
        Tooltip.install(spHome, homeTooltip);
    }


    @FXML
    private void goToHome(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("signin-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
            Stage mainStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            mainStage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
