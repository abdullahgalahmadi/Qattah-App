package Qatta_App;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static int countClient = 1;

    public static void main(String[] args) throws IOException {

        ServerSocket s = new ServerSocket(7897);

        while (true) {
            Socket client = s.accept();

            System.out.print("Client " + countClient + ": ");

            myThread m = new myThread(client);
            countClient++;
            m.start();

        }

    }
}

class myThread extends Thread {

    Socket client;

    public myThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try (ObjectInputStream ins = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream outs = new ObjectOutputStream(client.getOutputStream())) {

            User users = (User) ins.readObject();

            System.out.println(users.getName() + " Joined");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
