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
	List<String> userid;
	List<String> mid;
	List<String> lat;
	List<String> longi;
	String second;
	String third;
	String fourth;
	String fifth;
	
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
	      userid = new ArrayList<String>();
	      mid = new ArrayList<String>();
	      lat = new ArrayList<String>();
	      longi = new ArrayList<String>();
	      
	      second = "";
	      third = "";
	      fourth = "";
	 }
	
	
	public void insertgps() throws IOException
	{
		
		//split1(dis.readUTF());
		dm.insertlocation(second);
	}
	
	public void insertbeacon() throws IOException
	{
		
		split2(dis.readUTF());
		
		System.out.println(second + " " +third + " " +fourth + " " +fifth);
		
		if(dm.matchingId(second) == 1)
		{
			System.out.println("id exist");
			out2.println("id exist");
			out2.flush();
		}
		
		else if(!third.equals("24DDF411-8CF1-440C-87CD-E368DAF9C93E"))
		{
			System.out.println("fail uuid");
			out2.println("fail uuid");
			out2.flush();
		}
		
		else if(!fourth.equals("501"))
		{
			System.out.println("fail major");
			out2.println("fail major");
			out2.flush();
		}
		
		else if(dm.matchingminor(fifth) == 1)
		{
			System.out.println("minor exist");
			out2.println("minor exist");
			out2.flush();
		}
		else
		{
			dm.insertbeacon(second , third , fourth, fifth);
			System.out.println("complete");
			out2.println("complete");
			out2.flush();
		}
	}
	
	public void updatelocation() throws IOException, SQLException
	{
		String mm = dm.findid(dis.readUTF());
		
		System.out.println(mm);
		
		
		split0(dis.readUTF());
		System.out.println(second);
		System.out.println(third);
		dm.updatelocation(mm,second,third);
	}
	
	public void sendloc()throws IOException
	{
		
		dm.getloc(mid,lat,longi);
		
		
		try {
			
			
 			for(int i = 0 ; i < mid.size(); i++)
 			{
 				if(lat.get(i) != null)
 				{
 				out2.println(mid.get(i)+"/"+lat.get(i) +"/"+longi.get(i) + "/");
 				System.out.println(mid.get(i)+"/"+lat.get(i) +"/"+longi.get(i));
 				out2.flush();}
 				Thread.sleep(50);
 			}
 			
 			out2.println("end");
 			out2.flush();
 			
 			mid.clear();
 			lat.clear();
 			longi.clear();
		
		}catch(InterruptedException e){
				System.out.println(e.getMessage()); 
		}
	}
	
	
	public void sendbeacon()throws IOException
	{
		dm.getbeacon(userid, uid, major, minor);
		
		
		try {
 			
 			
 			for(int i = 0 ; i < uid.size(); i++)
 			{
 				out2.println(userid.get(i)+"/"+uid.get(i)+"/"+major.get(i) +"/"+minor.get(i) + "/");
 				System.out.println(userid.get(i)+"/"+uid.get(i)+"/"+major.get(i) +"/"+minor.get(i));
 				out2.flush();
 				Thread.sleep(50);
 			}
 			
 			userid.clear();
 			uid.clear();
 			major.clear();
 			minor.clear();
		
		}catch(InterruptedException e){
				System.out.println(e.getMessage()); 
		}
	}
	
	public void split0(String buffer)
    {
    		 second = buffer.split("/")[0];
    		 third  = buffer.split("/")[1];
    }
	
	public void split1(String buffer)
    {
    		 second = buffer.split("/")[0];
    		 third  = buffer.split("/")[1];
    		 fourth = buffer.split("/")[2];
    }
	
	public void split2(String buffer)
    {
    		 second = buffer.split("/")[0];
    		 third  = buffer.split("/")[1];
    		 fourth = buffer.split("/")[2];
    		 fifth = buffer.split("/")[3];
    }
	
}
