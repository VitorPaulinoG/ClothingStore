package com.vitorpg.clothingstore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StoreRootController {
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnProducts;
    @FXML
    private Button btnSale;

    private Button currentPageButton = null;

    @FXML
    public void initialize() {
        btnOverview.setOnAction(e -> setCurrentPage(btnOverview));
        btnProducts.setOnAction(e -> setCurrentPage(btnProducts));
        btnSale.setOnAction(e -> setCurrentPage(btnSale));

        setCurrentPage(btnOverview);
    }

    private void setCurrentPage(Button button) {
        if (currentPageButton != null) {
            currentPageButton.getStyleClass().remove("nav-button-current");
        }

        button.getStyleClass().add("nav-button-current");
        currentPageButton = button;
    }



}
