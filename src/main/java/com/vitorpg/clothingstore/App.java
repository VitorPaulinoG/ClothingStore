package com.vitorpg.clothingstore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        Font.loadFont(getClass().getResourceAsStream("/com/vitorpg/clothingstore/fonts/Inter-SemiBold.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/com/vitorpg/clothingstore/fonts/Khand-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/com/vitorpg/clothingstore/fonts/Ubuntu-Medium.ttf"), 16);

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("signin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
        stage.setTitle("ClothingStore - Cadastro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}