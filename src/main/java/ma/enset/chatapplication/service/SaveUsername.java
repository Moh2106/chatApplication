package ma.enset.chatapplication.service;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SaveUsername {

    public SaveUsername() {
    }

    private static final SaveUsername instance = new SaveUsername();

    private String name;
    private Socket socket;
    private final List<String> listUsers = new ArrayList<>();

    public static SaveUsername getInstance(){
        return instance;
    }

    public List<String> getlistUsers(){
        return listUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void listUsers(String nom){
        listUsers.add(nom);
    }

    /*public List<String> getListUsers() {
        return listUsers;
    }

    /*public void setListUsers(List<String> listUsers) {
        this.listUsers = listUsers;
    }*/
}
