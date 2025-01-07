package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.models.Sale;
import com.vitorpg.clothingstore.services.ProductService;
import com.vitorpg.clothingstore.services.SaleService;
import com.vitorpg.clothingstore.services.SupplyService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.Locale;

public class OverviewController {

    private SaleService saleService = new SaleService();
    private SupplyService supplyService = new SupplyService();
    private ProductService productService = new ProductService();


    @FXML
    private StackPane spHeader;

    @FXML
    private Label lbIntervalDate;

    @FXML
    private Label lbTotalRevenue;

    @FXML
    private Label lbSoldItems;

    @FXML
    private Label lbTotalProfit;

    @FXML
    public void initialize () {
        Image image = new Image(getClass().getResource("/com/vitorpg/clothingstore/images/header-background.png").toExternalForm());

        BackgroundImage background = new BackgroundImage(
                image,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        image.getWidth() * 0.3,
                        image.getHeight() * 0.3,
                        false,
                        false,
                        false,
                        false
                ));
        spHeader.setBackground(new Background(background));
        Rectangle clip = new Rectangle(background.getSize().getWidth(), background.getSize().getHeight());
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        spHeader.setClip(clip);
        spHeader.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            clip.setWidth(newBounds.getWidth());
            clip.setHeight(newBounds.getHeight());
        });
        showStatistics();
    }

    private void showStatistics () {
        var sales = saleService.findAll();
        lbTotalRevenue.setText(String.format(Locale.US, "R$ %.2f",
                sales.stream().map(Sale::getTotalPrice).reduce(0.0, Double::sum)));
        lbSoldItems.setText(sales.stream().map(Sale::getAmount).reduce(0L, Long::sum).toString());

        lbTotalProfit.setText(String.format(Locale.US, "R$ %.2f",
                sales.stream().map(x -> {
                    var supply = supplyService.findByProductId(x.getProduct().getId());
                    return  x.getTotalPrice() - (x.getAmount() * supply.getPrice());
                }).reduce(0.0, Double::sum)));
    }
}
