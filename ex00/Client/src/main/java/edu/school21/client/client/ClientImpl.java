package edu.school21.client.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientImpl implements Client{
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner reader;
    private String ip;
    private int port;

    public ClientImpl(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void connect() {
        try{
            socket = new Socket(ip, port);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            reader = new Scanner(socket.getInputStream());
        }
        catch (IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void sendMessage(String message){
        printWriter.println(message);
    }

    public void receiveMessage(){
        if (reader.hasNextLine()){
            String message = reader.nextLine();
            System.out.println(message);
            if (message.equalsIgnoreCase("Successful!")){
                stopClient();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void stopClient(){
        try {
            socket.close();
        }
        catch (IOException e){
            System.out.println("Probably socket already closed");
        }
        reader.close();
        printWriter.close();
        System.exit(0);
    }
}
