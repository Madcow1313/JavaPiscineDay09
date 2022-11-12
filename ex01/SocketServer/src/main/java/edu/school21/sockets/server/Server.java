package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Component
public class Server {
    private UsersService usersService;
    private ServerSocket socket;
    private Socket clientSocket;
    private Scanner reader;
    private PrintWriter printer;

    @Autowired
    public Server(UsersService usersService){
        this.usersService = usersService;
    }

    public void startServer(int port) throws IOException {
        socket = new ServerSocket(port);
        while (true){
            clientSocket = socket.accept();
            System.out.println("New connection");
            printer = new PrintWriter(clientSocket.getOutputStream(), true);
            printer.println("Hello from Server!");
            reader = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
            handleMessage();
        }
    }

    private void handleMessage() throws IOException {
        String message;

        if (!reader.hasNextLine()){
            stopConnection();
            return;
        }
        message = reader.nextLine().trim();
        System.out.println(message);
        if (message.equalsIgnoreCase("signup")){
            printer.println( "Enter username:");
            handleRegistration();
        }
        else if (message.equalsIgnoreCase("signin")){
            printer.println("Enter username:");
            handleSignIn();
        }
        else{
            printer.println("Unknown command");
        }
    }

    public void stopConnection() throws IOException{
        clientSocket.close();
        printer.close();
        reader.close();
        System.out.println("Disconnected");
    }

    public void stopServer() throws IOException {
        socket.close();
    }

    public void handleRegistration() throws IOException{
        String name = "";
        String password = "";

        while ((reader.hasNextLine() && password.length() == 0)){
            String message = reader.nextLine().trim();
            if (name.length() == 0){
                name = message.trim().toLowerCase();
                printer.println("Enter password:");
            }
            else if (password.length() == 0){
                password = message;
                printer.println("Successful!");
            }
        }
        if (name.length() == 0 || password.length() == 0){
            stopConnection();
            return;
        }
        User user = usersService.createNewUser(name, password);
        usersService.saveUser(user);
        stopConnection();
    }

    private void handleSignIn() throws IOException{
        String message;

        if (!reader.hasNextLine() || usersService.findAll().isEmpty()){
            printer.println("No users registered");
            stopConnection();
            return;
        }
        message = reader.nextLine();
        if (usersService.findUserByName(message.trim()).isPresent() && reader.hasNextLine()){
            User user = usersService.findUserByName(message.trim()).get();
            printer.println("Enter password:");
            message = reader.nextLine();
            if (usersService.getPasswordEncoder().encode(message).equals(user.getPassword())){
                return;
            }
        }
        printer.println("Wrong password");
        stopConnection();
    }
}
