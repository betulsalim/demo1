package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<ClientHandler> clientsArrayList = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket=new ServerSocket(9090);
            while(true) {
                System.out.println("Waiting for clients....");
                socket = serverSocket.accept();
                System.out.println("Connected!");
                ClientHandler clientThread = new ClientHandler(socket, clientsArrayList);
                clientsArrayList.add(clientThread);
                clientThread.start();
            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}