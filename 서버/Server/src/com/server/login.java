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
           
	      System.out.println( second + " "  + third  );
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
	   
	   public String login() throws IOException
	   {
		   int check = 0;
		   split(dis.readUTF());
		   
		   check = dm.matchingIdPasswd(second, third);
		   
		   if(check == 1)
		   {
			   dos.writeUTF("login complete");
			   return second;
		   }
		   else
		   {
			   dos.writeUTF("아이뒤와 비밀번호가 일치하지 않습니다.");
			   return null;
		   }
		   
	   }
	   
	   public void split(String buffer)
	    {
	    	
	    	
	    		 second = buffer.split("/")[0];
	    		 third  = buffer.split("/")[1];
	    		 
	    	
	    	

	    }

}
