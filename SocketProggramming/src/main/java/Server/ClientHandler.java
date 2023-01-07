package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.example.socketproggramming.HelloController;
import com.example.socketproggramming.User;



public class ClientHandler extends Thread{
    User user = new User();

    private ArrayList<ClientHandler> clientsArrayList = new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientsArrayList) {
        try {
            this.socket=socket;
            this.clientsArrayList=clientsArrayList;
            this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.user.nameString = reader.readLine();
            clientsArrayList.add(this);


        }catch (IOException exception) {
            closeEverything(socket,reader,writer);
        }
    }

    @Override

    public void run() {
        String message;
        try {

           while(socket.isConnected()){
               try{
                   message = reader.readLine();
                   broadcastMessage(message);
               }catch (IOException exception){
                   closeEverything(socket,reader,writer);
                   break;
               }
           }



        }catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    public void broadcastMessage(String messageToSend){
       for(ClientHandler clientHandler : clientsArrayList){
           try{
               if(!clientHandler.user.nameString.equals(user.nameString)){
                   clientHandler.writer.write(messageToSend);
                   clientHandler.writer.newLine();
                   clientHandler.writer.flush();
               }
           }catch (IOException exception){
               exception.printStackTrace();
           }
       }

    }

    public void removeClientHandler(){
        clientsArrayList.remove(this);
        broadcastMessage("SERVER: " +user.nameString+" has left!");

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