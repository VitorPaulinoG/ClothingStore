package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ImageView imgPicture;

    @FXML
    public void initialize() {

//        imgPicture.setEffect(null);
    }

    @FXML
    public void login() {
        if(txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null); // Sem título
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        // Autenticar

        // Entrar no Sistema
        try {
            FXMLLoader storeFxmlLoader = new FXMLLoader(App.class.getResource("store-root-view.fxml"));
            Scene storeScene = new Scene(storeFxmlLoader.load());
            Stage mainStage = App.mainStage;
            mainStage.setTitle("ClothingStore");
            mainStage.setScene(storeScene);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro Interno");
            alert.setHeaderText(null);
            alert.setContentText("Erro de IO");
        }

    }

    @FXML
    public void goToSignIn() {
        try {
            FXMLLoader loginFxmlLoader = new FXMLLoader(App.class.getResource("signin-view.fxml"));
            Scene loginScene = new Scene(loginFxmlLoader.load());
            Stage mainStage = App.mainStage;
            mainStage.setTitle("ClothingStore - Cadastro");
            mainStage.setScene(loginScene);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro Interno");
            alert.setHeaderText(null);
            alert.setContentText("Erro de IO");
        }
    }
}
