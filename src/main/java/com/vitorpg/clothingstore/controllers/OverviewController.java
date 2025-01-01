package com.vitorpg.clothingstore.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class OverviewController {

    @FXML
    private StackPane spHeader;

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
    }
}
