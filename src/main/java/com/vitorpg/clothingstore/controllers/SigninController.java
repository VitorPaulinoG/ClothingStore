package com.vitorpg.clothingstore.controllers;


import com.vitorpg.clothingstore.App;
import com.vitorpg.clothingstore.models.User;
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

public class SigninController {

    private UserService userService = new UserService();

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
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
    public void signIn(){
        if(txtName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Aviso");
            alert.setHeaderText(null); // Sem título
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        if(!txtConfirmPassword.getText().equals(txtPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("As senhas digitadas não coincidem. Por favor, tente novamente.");
            alert.showAndWait();
            return;
        }

        User user = new User();
        user.setName(txtName.getText());
        user.setEmail(txtEmail.getText());
        user.setPassword(txtPassword.getText());

        userService.save(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        var alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add("/main-styles.css");
        alert.setTitle("Caixa de Contexto");
        alert.setHeaderText("Usuário salvo com sucesso!");
        alert.setContentText("Agora você pode fazer login com esse usuário!");
        alert.showAndWait();
    }

    @FXML
    public void goToLogin() {
        try {
            FXMLLoader loginFxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
            Scene loginScene = new Scene(loginFxmlLoader.load());
            Stage mainStage = App.mainStage;
            mainStage.setTitle("ClothingStore - Login");
            mainStage.setScene(loginScene);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            var alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add("/main-styles.css");
            alert.setTitle("Erro Interno");
            alert.setHeaderText(null);
            alert.setContentText("Erro de IO");
        }
    }
}