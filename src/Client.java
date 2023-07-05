import javafx.application.Platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static PrintWriter printWriter;
    static String name2;
    public static void main(String[] args) throws IOException {
        boolean bool=true;
        Scanner input=new Scanner(System.in);
        System.out.println("name:");
        String name1=input.nextLine();
        Socket socket=new Socket("127.0.0.1",12345);
        printWriter=new PrintWriter(socket.getOutputStream(),true);
        Scanner serverInput=new Scanner(socket.getInputStream());
        printWriter.println(name1);
        while(bool) {
            System.out.println("name2:");
            name2 = input.next();
            printWriter.println(name2);
            String msg1 = serverInput.nextLine();
            System.out.println(msg1);
            if (msg1.equals("you can start")) {
                break;
            } else {
                System.out.println(name2 + " is offline. do you want connect other?");
                String answer = input.next();
                printWriter.println(answer);
                if (answer.equals("no")) {
                    bool=false;
                    System.out.println("wait for others");
                }
            }
        }
        if(!bool) {//wait for others
            name2=serverInput.nextLine();
        }
        new Thread(() -> {
            while (true){
                String msg= serverInput.nextLine();
                System.out.println(msg);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Program.controller.showChatMsg(name2,msg);
                    }
                });
            }
        }).start();
        Program.run(name1);
    }
}
