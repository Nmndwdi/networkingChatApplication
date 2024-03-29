import java.net.*;
import java.util.*;
import java.io.*;

class second {
    Socket socket;
    Scanner sc;
    PrintWriter out;
    boolean check = true;

    public second(int port, String ipaddress) {
        try {
            socket = new Socket(ipaddress, port);
            System.out.println("Connection established...");
            sc = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            while (true) {
                try {
                    String msg = sc.nextLine();
                    if (msg.equals("exit")) {
                        check = false;
                        break;
                    }
                    System.out.println("other : " + msg);
                } catch (Exception e) {
                    break;
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            while (true) {
                try {
                    if (check == false)
                        break;
                    Scanner sc1 = new Scanner(System.in);
                    String content = sc1.nextLine();
                    out.println(content);
                    out.flush();
                } catch (Exception e) {
                    break;
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter the target port address");
        int port = Sc.nextInt();
        System.out.println("Enter target ip");
        String ipaddress = Sc.next();
        new second(port, ipaddress);
    }
}