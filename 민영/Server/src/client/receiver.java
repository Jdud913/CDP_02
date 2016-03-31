package client;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
 
public class receiver extends Thread {
	Socket socket;
    DataInputStream in;
 
    receiver(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }
 
    @Override
    public void run() {
        while (in != null) {
            try {
                System.out.println(in.readUTF());
            } catch (IOException e) {
            }
        }
    }
}
