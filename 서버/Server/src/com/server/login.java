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

public class login {
	
		private Socket socket;
		BufferedWriter networkWriter;PrintWriter out2;
	   InputStream in;
	   OutputStream out ;
	   DataInputStream dis;
	   DataOutputStream dos ;
	   DBmanager dm = new DBmanager();
	   String first = "";
		String second = "";
		String third = "";
		String forth = "";
		String fifth = "";
		String sixth = "";
		String Id = "";
    	String passwd = "";
    	String phone = "";
    	String mom = "";
    	String flag = "";
	   
	   public login(Socket socket)
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
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         } 
	   }
	   
	   
	   public void SignUp() throws IOException
	   {
		  split(dis.readUTF());                  
           
	      
	      Id = second;
	      passwd = third;
	      
	      int confirm = dm.idExist(second);
	           	
	      if(confirm == 1)
	      {
	    	  System.out.println("id 가 있어 아뒤 못만듭니다 ");
	          dos.writeUTF("id 가 있어 아뒤 못만듭니다 ");
	          dos.flush();
	      }
	      else
	      {
	    	  dos.writeUTF("회원가입 ");
	    	  dos.flush();
	          System.out.println("id 없음 가입가능 ");
	          dm.insertUserData(second, third);
	      }
           	    
	   }
	   
//	   public String login() throws IOException
//	   {
//		   int check = 0;
//		   split(dis.readUTF());
//		   System.out.println(second + "/" +third);
//		   check = dm.matchingIdPasswd(second, third);
//		   
//		   if(check == 1)
//		   {
//			   //dos.writeUTF("1/4");
//			   System.out.println("1/4");
//			   out2.println("1"+"/"+"4");
//			   out2.flush();
//			   return second;
//		   }
//		   else
//		   {
//			   //dos.writeUTF("1/5");
//			   out2.println("1"+"/"+"5");
//			   out2.flush();
//			   System.out.println("1/5");
//			   return null;
//		   }
//		   
//	   }
	   
	   public void mlogin() throws IOException
	   {
		   int check = 0;
		   int check1 = 0; 
		   split(dis.readUTF());
		   System.out.println(second + "/" +third);
		   check = dm.matchingmId(second);
		   check1 = dm.matchingmpasswd(third);
		   
		   if(check == 1 && check == 1)
		   {
			   //dos.writeUTF("1/4");
			   System.out.println("complete");
			   out2.println("complete");
			   out2.flush();
		   }
		   else if(check != 1)
		   {
			   //dos.writeUTF("1/5");
			   out2.println("id error");
			   System.out.println("id error");
			   out2.flush();
			   //System.out.println("error");
		   }
		   else if(check != 1)
		   {
			   out2.println("passwd error");
			   System.out.println("passwd error");
			   out2.flush();
		   }
		   else if(check == 1 && check == 1)
		   {
			   out2.println("id & passwd error");
			   System.out.println("passwd error");
			   out2.flush();
		   }
		   
	   }
	   
	   
	   public void split(String buffer)
	    {
	    	
	    	
	    		 second = buffer.split("/")[0];
	    		 third  = buffer.split("/")[1];
	    }

}
