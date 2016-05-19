package com.server;


import java.io.BufferedInputStream;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


 
public class Server implements Runnable {
    
	ServerSocket serverSocket;
    Thread[] threadArr;
    String first = "";
	String second = "";
	String third = "";
	String forth = "";
	String fifth = "";
	String sixth = "";
	DBmanager dm = new DBmanager();
	login log;
	GPS gps;
 
    public static void main(String[] args) {
    	Server server = new Server(10);
        server.start();
    }
 
    public Server(int num) {
        try {
            
            serverSocket = new ServerSocket(7777);            
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
            	String Id = "";
            	String passwd = "";
                System.out.println(getTime() + " 활성화 됨.");
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + " " + socket.getInetAddress()
                        + "접속을 했습니다");
                
                log = new login(socket);
                gps = new GPS(socket);
                
                
                InputStream in = socket.getInputStream();    
                DataInputStream dis = new DataInputStream(in);
                
                OutputStream out = socket.getOutputStream();  
                DataOutputStream dos = new DataOutputStream(out); 
                
             	BufferedWriter networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             	PrintWriter out2 = new PrintWriter(networkWriter, true);
             	
                while(true)
                {
                	//dos.writeUTF("번호를 주세요");
                	System.out.println("번호를 주세요");
	                int i = Integer.parseInt(dis.readUTF());
	                System.out.println(i);
                
                switch(i)
                {
                
	                case 1:
	                {
						gps.insertbeacon();
						
	                	gps.insertgps();
	                     break;       
	                }
	                case 2:
	                {
	                	log.mlogin();
	                	break;
	                }
	                case 3:
	                {
	                	try {
							gps.updatelocation();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	break;
	                }
	                case 4:
	                {
	                	gps.sendloc();
	                	break;
	                }
	                case 5:
	                {
	                	gps.sendbeacon();
	                }
	                default :
	                {
	                	break;
	                }
	                
                        
                }
                
                if(i <0)
                	break;
                
                }
                socket.close();
               
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    static String getTime() {
        String name = Thread.currentThread().getName();
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date()) + name;
    }
    
    public int split(String buffer)
    {
    	
    	 first = buffer.split("/")[0];
    	
    	if(first.equals("0"))
    	{
    		 second = buffer.split("/")[1];
    		 third  = buffer.split("/")[2];
    		 
    		 return 0;
    	}
    	else if(first.equals("1"))
    	{
    		 second = buffer.split("/")[1];
    		 third  = buffer.split("/")[2];
    		 forth  = buffer.split("/")[3];
    		 fifth  = buffer.split("/")[4];
    		 sixth  = buffer.split("/")[5];
    		 
    		 return 1;

    	}
    	
    	return -1;
    }
    
    
    
    
    
}