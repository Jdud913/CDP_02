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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GPS {

	private Socket socket;
	BufferedWriter networkWriter;PrintWriter out2;
	InputStream in;
	OutputStream out ;
	DataInputStream dis;
	DataOutputStream dos ;
	DBmanager dm = new DBmanager();
	String location = "";
	String msg = "";
	String bid = "";
	List<String> uid;
	List<String> major;
	List<String> minor;
	String second;
	String third;
	
	public GPS(Socket socket)
	{
	      this.socket = socket;
	      
	      try {
	    	  dm.startDatabase();
	          in = socket.getInputStream();   
	          out = socket.getOutputStream();
	          dis = new DataInputStream(in);
	          dos = new DataOutputStream(out); 
	          networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	          out2 = new PrintWriter(networkWriter, true);
	         } catch (IOException e) {
	            e.printStackTrace();
	         } 
	      uid = new ArrayList<String>();
	      major = new ArrayList<String>();
	      minor = new ArrayList<String>();
	      
	      second = "";
	      third = "";
	 }
	
	
	public void insertgps(String id) throws IOException
	{
		
		split(dis.readUTF());
		dm.insertlocation(second , third);
	}
	
	public void sendmsg()throws IOException
	{
		dm.getbeacon(uid, major, minor);
		
		
		try {
 			
 			
 			for(int i = 0 ; i < uid.size(); i++)
 			{
 				out2.println(uid.get(i)+"/"+major.get(i) +"/"+minor.get(i) + "/");
 				System.out.println(uid.get(i)+"/"+major.get(i) +"/"+minor.get(i));
 				out2.flush();
 				Thread.sleep(50);
 				/*
 				out2.println(major.get(i));
 				System.out.println(major.get(i));
 				out2.flush();
 				Thread.sleep(50);
				out2.println(minor.get(i));
				System.out.println(minor.get(i));
				out2.flush();
				Thread.sleep(50);*/
 			}
		
		}catch(InterruptedException e){
				System.out.println(e.getMessage()); 
		}
	}
	
	public void closegps(String id)
	{
		dm.insertlocation(id, "x");
	}
	
	public void split(String buffer)
    {
    	
    	
    		 second = buffer.split("/")[0];
    		 third  = buffer.split("/")[1];
    }
	
}
