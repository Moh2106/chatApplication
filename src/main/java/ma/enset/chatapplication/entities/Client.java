package ma.enset.chatapplication.entities;

import java.net.Socket;

public class Client {
    private String name;
    private Socket s;

    public Client() {
    }

    public Client(String name, Socket s) {
        this.name = name;
        this.s = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", s=" + s +
                '}';
    }
}
