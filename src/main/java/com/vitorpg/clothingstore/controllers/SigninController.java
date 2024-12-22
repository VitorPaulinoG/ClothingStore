package com.vitorpg.clothingstore.controllers;


import com.vitorpg.clothingstore.App;
import com.vitorpg.clothingstore.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class SigninController {
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
    public void initialize() {

//        imgPicture.setEffect(null);
    }

    @FXML
    public void signIn(){
        if(txtName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null); // Sem título
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        if(!txtConfirmPassword.getText().equals(txtPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null); // Sem título
            alert.setContentText("As senhas digitadas não coincidem. Por favor, tente novamente.");
            alert.showAndWait();
            return;
        }

        User user = new User(txtName.getText(), txtEmail.getText(), txtPassword.getText());

        // Cadastrar Usuário


        // Remover Isso Abaixo
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(
            "O usuário foi criado com sucesso! \n" +
            "Name: " + user.getName() + "\n" +
            "Email: " + user.getEmail() + "\n" +
            "Password: " + user.getPassword() + "\n"
        );
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
            alert.setTitle("Erro Interno");
            alert.setHeaderText(null);
            alert.setContentText("Erro de IO");
        }
    }
}