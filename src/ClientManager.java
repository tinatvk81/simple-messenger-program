import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientManager extends Thread {
    private Socket socket;
    private String name1;
    private String name2;
    private Scanner input;
    private PrintWriter output;
    public ClientManager(String name,Socket socket) throws IOException {
        this.socket = socket;
        name1=name;
        input=new Scanner(socket.getInputStream());
        output=new PrintWriter(socket.getOutputStream(),true);
        start();
    }

    @Override
    public void run() {
        boolean wait=false;
        while (true) {
            if (!wait) {
                name2 = input.nextLine();
                System.out.println("name2:"+name2);
            }
            if (Server.clients.containsKey(name2)) {
                if(wait)
                    output.println(name2);
                else
                    output.println("you can start");
                System.out.println(name1+ " connected to "+ name2+".");
                ClientManager otherClient=Server.clients.get(name2);
                otherClient.name2=name1;
                while(socket.isConnected()){
                    try {
                        String msg = input.nextLine();
                        System.out.println("message from " + name1 + " to " + name2 + ":" + msg);
                        otherClient.output.println(msg);
                    } catch (NoSuchElementException e) {
                        System.err.println(name1+" disconnected");
                        break;
                    }
                }

            }else{
                if(!wait){
                    output.println("fail");
                    String msg = input.nextLine();
                    if (msg.equals("no"))
                        wait=true;
                }else
                    System.out.println("wait");
            }

        }
    }
}
