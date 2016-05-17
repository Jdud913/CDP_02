package client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class client {
	 
	public static void main(String[] args) {
        try{
            String serverIp = "20.20.3.154";
            Socket socket = new Socket(serverIp, 7777);
             
            System.out.println("서버에 연결되었습니다.");
            sender sender = new sender(socket);
            receiver receiver = new receiver(socket);
             
            sender.start();
            receiver.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}




