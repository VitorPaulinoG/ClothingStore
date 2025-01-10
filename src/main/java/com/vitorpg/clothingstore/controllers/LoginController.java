package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.App;
import com.vitorpg.clothingstore.exceptions.EntityNotFoundException;
import com.vitorpg.clothingstore.services.SessionManager;
import com.vitorpg.clothingstore.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private UserService userService = new UserService();

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ImageView imgPicture;

    @FXML
    private Rectangle clip;

    @FXML
    private StackPane spPicture;

    @FXML
    public void initialize() {
        clip.setArcWidth(16);
        clip.setArcHeight(16);
        clip.heightProperty().bind(spPicture.heightProperty());
    }

    @FXML
    public void login() {
        if(txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Aviso");
            alert.setHeaderText(null); // Sem título
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }
        var email = txtEmail.getText();
        var password = txtPassword.getText();
        try {
            if (userService.authenticate(email, password)) {
                SessionManager.getInstance().login(userService.findFirstByEmail(email));
                goToStoreRoot();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Erro");
            alert.setHeaderText("Senha Inválida");
            alert.setContentText("A senha digitada não está correta! \nTente novamente.");
            alert.showAndWait();
        } catch (EntityNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Erro");
            alert.setHeaderText("Usuário não encontrado.");
            alert.setContentText(ex.getMessage() + "\nTente novamente.");
            alert.showAndWait();
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
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Erro Interno");
            alert.setHeaderText(null);
            alert.setContentText("Erro de IO");
            alert.showAndWait();
        }
    }

    private void goToStoreRoot() {
        try {
            FXMLLoader storeFxmlLoader = new FXMLLoader(App.class.getResource("store-root-view.fxml"));
            Scene storeScene = new Scene(storeFxmlLoader.load());
            Stage mainStage = App.mainStage;
            mainStage.setTitle("ClothingStore");
            mainStage.setScene(storeScene);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Erro Interno");
            alert.setHeaderText(null);
            alert.setContentText("Erro de IO");
            alert.showAndWait();
        }
    }

}
