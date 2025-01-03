package com.vitorpg.clothingstore;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import com.vitorpg.clothingstore.repositories.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class App extends Application {
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        DbConnection.loadConfig("production");

//        ImageDao imageDao = new ImageDao();
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/casual-shirt.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 1L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/formal-pants.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 2L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/sports-jacket.jpg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 3L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/leather-shoes.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 4L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/sueter-business-casual.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 5L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/bermuda-athleisure.jpg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 6L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/casaco-streetwear.png");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 7L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/chapeu-chic.png");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 8L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/suéter-lã.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 9L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/blusa-seda.jpg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 10L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/jaqueta-nylon.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 11L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/moletom-fleece.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 12L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/calça-cargo-verde.jpg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 13L);
//
//        imageDao.addToProduct(new Image() {{
//            File file = new File("/home/vitorpg/Downloads/image-test/saia-amarela.jpeg");
//            setData(Files.readAllBytes(file.toPath()));
//            setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
//        }}, 14L);






        Font.loadFont(getClass().getResourceAsStream("/com/vitorpg/clothingstore/fonts/Inter-SemiBold.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/com/vitorpg/clothingstore/fonts/Khand-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/com/vitorpg/clothingstore/fonts/Khand-Medium.ttf"), 16);
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