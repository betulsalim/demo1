package Server;

import com.example.socketproggramming.HelloApplication;
import com.example.socketproggramming.HelloController;
import com.example.socketproggramming.User;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends User {
    User user =new User();

    ClientHandler clientHandler;
    private Socket socket;

    private BufferedReader reader;

    private BufferedWriter writer;

    public Client (Socket socket){
        try{
            this.socket =socket;
            this.writer =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader =new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }catch (IOException e){
          closeEverything(socket,reader,writer);
        }
    }
    public void sendMessage(){
        try{
            Scanner scanner = new Scanner(System.in);

            while(socket.isConnected()){
                String messageToSend = scanner.nextLine();
                writer.write(user.nameString+ " : "+messageToSend);
              writer.newLine();
              writer.flush();
            }


        } catch (IOException e) {
           closeEverything(socket,reader,writer);
        }
    }
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;

                while(socket.isConnected()){
                    try{
                        messageFromGroupChat = reader.readLine();
                        System.out.println(messageFromGroupChat);

                    } catch (IOException e) {
                        closeEverything(socket,reader,writer);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer){
        try{
            if(reader != null){
                reader.close();
            }if(writer != null){
                writer.close();
            }if(socket != null){
                socket.close();
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }



}
