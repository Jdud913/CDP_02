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
import java.util.Date;


public class GPS {

	private Socket socket;
	BufferedWriter networkWriter;PrintWriter out2;
	InputStream in;
	OutputStream out ;
	DataInputStream dis;
	DataOutputStream dos ;
	DBmanager dm = new DBmanager();
	String location = "";
	
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
	 }
	
	
	public void insertgps(String id) throws IOException
	{
		location = dis.readUTF();
		dm.insertlocation(id, location);
	}
	
	public void closegps(String id)
	{
		dm.insertlocation(id, "x");
	}
	
}
