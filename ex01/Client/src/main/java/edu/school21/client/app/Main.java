package edu.school21.client.app;

import edu.school21.client.client.ClientImpl;

import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        if (args.length != 1 || !args[0].substring(0, "--server-port=".length()).equals("--server-port=")){
            System.out.println("Wrong input, port required: --server-port=8081");
            System.exit(-1);
        }
        else{
            String port = args[0].substring("--server-port=".length());
            ClientImpl client = new ClientImpl("localhost", Integer.parseInt(port));
            Scanner scanner = new Scanner(System.in);
            client.connect();
            while(true){
                if (client.getSocket().isClosed())
                    break ;
                client.receiveMessage();
                if (scanner.hasNextLine()){
                    client.sendMessage(scanner.nextLine());
                }
            }
        }
    }
}
