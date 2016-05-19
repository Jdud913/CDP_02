package com.server;

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
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mserver implements Runnable {

	ServerSocket serverSocket;
	DBmanager dm = new DBmanager();
	Thread[] threadArr;
	Search search;
	
	login log;
	GPS gps;
	
	
	 static String getTime() {
	        String name = Thread.currentThread().getName();
	        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
	        return f.format(new Date()) + name;
	 }
	
	public static void main(String[] args) {
    	Server server = new Server(10);
        server.start();
    }
 
    public Mserver(int num) {
        try {
            
            serverSocket = new ServerSocket(8888);
            System.out.println(getTime() + " 준비중");
            
        	dm.startDatabase();
            threadArr = new Thread[num];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void start() {
        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread(this);
            threadArr[i].start();
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(getTime() + " 활성화 됨.");
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + " " + socket.getInetAddress()
                        + "접속을 했습니다");
                
                
                search = new Search(socket);
                log = new login(socket);
                gps = new GPS(socket);
                
                
                InputStream in = socket.getInputStream();    
                DataInputStream dis = new DataInputStream(in);
                
             	BufferedWriter networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             	PrintWriter out2 = new PrintWriter(networkWriter, true);
             	
                while(true)
                {
	
	                int i = Integer.parseInt(dis.readUTF());
	                System.out.println(i);
                
	                switch(i)
	                {
	                
	                	case 1:
	                	{
	                		log.mlogin();
	                		break;
	                	}
		                case 2:
		                {
		                	gps.insertbeacon();
		                	break;
		                }
		                case 3:
		                {
		                	gps.sendloc();
		                	break;
		                }
		                default:
		                {
		                	break;
		                }
		                
	                        
	                }
                
	                if(i  == 0)
	                	break;
                
                }
                
                
                search.LInit();
                socket.close();
               
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
}
