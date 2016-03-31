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

public class Search {

	private Socket socket;
	BufferedWriter networkWriter;PrintWriter out2;
	InputStream in;
	OutputStream out ;
	DataInputStream dis;
	DataOutputStream dos ;
	DBmanager dm = new DBmanager();
	String id = "";
	String ploc = "";
	public Search(Socket socket)
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
	
	public void FindPerson() throws IOException
	{
		id  = dis.readUTF();
		ploc = dm.getPlocation(id);
		
		while(	ploc.equals("x") || dis.readUTF().equals("stop") )
		{
			if(ploc.equals("x"));
			else
			{
				dos.writeUTF(ploc);
			}
		}
	
		
	}
	
	public void FindPeople() throws IOException
	{
		int count = dm.locationnum();
		String temp = "";
		String [][]loc = new String[count][2];
		
		while(true)
		{
			if(dis.readUTF().equals("stop"))
				break;
			
			loc = dm.getPeopleL();
			
			for(int i = 0 ; i <count ; i++)
			{
				if(!loc[i][1].equals("x"))
				{
					temp = loc[i][0]+"/"+loc[i][1];
					dos.writeUTF(temp);
				}
			}
			try{
				Thread.sleep(500);
			}catch(InterruptedException e)
			{
				System.out.println(e.getMessage());
			}
			
		}
	}

	public void LInit()
	{
		dm.endlocation();
	}
	
}
