package ma.enset.chatapplication.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ma.enset.chatapplication.HelloApplication;
import ma.enset.chatapplication.service.SaveUsername;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionPageControllers implements Initializable {
    @FXML
    private BorderPane borderPanePage;
    @FXML
    private TextField username_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void loginButton(ActionEvent actionEvent) throws IOException {
        Socket s = new Socket("localhost", 1234);
        SaveUsername.getInstance().setSocket(s);

        borderPanePage.getScene().getWindow().hide();
        // recuperation du nom du client
        String username = username_text.getText();
        SaveUsername saveUsername = SaveUsername.getInstance();
        //Sauvegarde du nom du client pour pouvoir l'utiliser tout au long du démarrage du serveur
        saveUsername.setName(username);

        // Démarrage de l'interface ChatPage.fxml
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChatPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setScene(scene);
        stage.show();


    }


}
