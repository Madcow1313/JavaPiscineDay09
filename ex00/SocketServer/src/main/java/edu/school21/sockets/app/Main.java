package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.sockets");
        if (args.length != 1 || !args[0].substring(0, "--port=".length()).equals("--port=")){
            System.out.println("Wrong input, port required: --server-port=8081");
            System.exit(-1);
        }
        else{
            String port = args[0].substring("--port=".length());
            Server server = new Server(context.getBean(UsersService.class));
            try {
                server.startServer(Integer.parseInt(port));
            }
            catch (Exception e){
                System.err.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}
