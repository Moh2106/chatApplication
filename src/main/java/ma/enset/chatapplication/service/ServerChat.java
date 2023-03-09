package ma.enset.chatapplication.service;

import ma.enset.chatapplication.entities.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerChat extends Thread {
    // Liste des clients connectés
    public List<Client> clientList = new ArrayList<>();
    public List<String> clientNom = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new ServerChat().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);

            while (true){
                Socket s = ss.accept();
                System.out.println("New connection started");
                new Conversation(s).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class Conversation extends Thread{
        private Socket socket;

        public Conversation(Socket socket) {
            this.socket = socket;
        }


        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                PrintWriter pw = new PrintWriter(os, true);

                String usernameEnvoye;

                // recuperation du username du client dans le server
                if((usernameEnvoye = br.readLine()) != null){
                    Client client = new Client(usernameEnvoye, socket);
                    clientList.add(client);

                    System.out.println(clientList);

                }

                String request ;

                // Récuperation de tous les messages envoyés par les clients
                while ((request = br.readLine()) != null){
                    String[] contenuMessage = request.split("=>");
                    String message = contenuMessage[0];
                    String destinataire = contenuMessage[1];
                    String username = contenuMessage[2];

                    broadCast(this.socket, message, clientList, destinataire, username);

                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void broadCast(Socket s, String message, List<Client> clients, String destinataire, String username) throws IOException {
        for (Client c: clients) {
            Socket socket = c.getS();
            String nom = c.getName();

            if(s != socket && nom.equals(destinataire)){
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println(message+"=>"+username);
            }

            if(s != socket && destinataire.equals("vide")){
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println(message+"=>"+username);
            }

        }
    }
}
