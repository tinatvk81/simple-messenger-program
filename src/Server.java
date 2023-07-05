import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    public static HashMap<String,ClientManager> clients=new HashMap<>();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(12345);
        System.out.println("server started!");
        while (true){
            Socket socket= serverSocket.accept();
            Scanner input=new Scanner(socket.getInputStream());
            String name= input.nextLine();
            System.out.println(name+" connected");
            ClientManager c=new ClientManager(name,socket);
            clients.put(name,c);
        }

    }
}
