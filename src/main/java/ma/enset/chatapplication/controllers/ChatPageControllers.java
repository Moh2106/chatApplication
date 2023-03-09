package ma.enset.chatapplication.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ma.enset.chatapplication.entities.Client;
import ma.enset.chatapplication.service.SaveUsername;
import ma.enset.chatapplication.service.ServerChat;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ChatPageControllers implements Initializable {

    @FXML
    Label message;

    @FXML
    ListView<String> listViewMessage = new ListView<>();

    @FXML
    TextField messageText;
    @FXML
    TextField destinataire;

    // recuperation du username du client
    String username = SaveUsername.getInstance().getName();

    // recupération du socket du client
    Socket socket = SaveUsername.getInstance().getSocket();


    InputStream is = socket.getInputStream();
    OutputStream os = socket.getOutputStream();

    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);

    PrintWriter pw = new PrintWriter(os, true);

    public ChatPageControllers() throws IOException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //SaveUsername.getInstance().getlistUsers().add(username);
        //System.out.println(username);
        message.setText(username);

        // Démarrage du thread Conversation
        new Conversation(socket, username).start();

    }

    public void sendButton(ActionEvent actionEvent) {
        String message = messageText.getText();
        String to ;
        if (destinataire.getText().isEmpty()){
            to = "vide";
        } else {
            to = destinataire.getText();
        }

        String messageComplet = message+"=>"+to+"=>"+username;
        pw.println(messageComplet);
        messageText.setText("");
    }

    class Conversation extends Thread{
        private Socket s;
        private String username;
        Conversation(Socket s, String username){
            this.s = s;
            this.username = username;
        }

        @Override
        public void run() {
            try {
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();

                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                PrintWriter pw = new PrintWriter(os, true);

                pw.println(username);

                String messageRecu;
                ObservableList<String> data = FXCollections.observableArrayList();

                while (true){
                    messageRecu = br.readLine();
                    String[] messageRecuTab = messageRecu.split("=>");
                    String contenuMessage = messageRecuTab[0];
                    String nomEnvoyeur = messageRecuTab[1];

                    System.out.println(messageRecu);
                    Label envoi = new Label(nomEnvoyeur + "  " + new Date() + " " +"\n" +contenuMessage);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            listViewMessage.getItems().add(envoi.getText());
                        }
                    });


                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
