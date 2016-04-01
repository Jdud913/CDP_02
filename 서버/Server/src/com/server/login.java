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
		   int n = split(dis.readUTF());            
           System.out.println(n);        
           if (n == 0)
           {
	           	System.out.println( second + " "  + third );
	           	int confirm = dm.matchingIdPasswd(second, third);
	           	
	           	if(confirm == 1)
	           	{
	           		System.out.println("id 가 존재 합니다");
	           		Id = second;
	               	passwd = third;
	               	out2.println("1"+"/"+flag);
	           		out2.flush();
	           	}
	           	else
	           	{
	           		System.out.println("1/id 가 존재하지 않습니다");
	           		out2.println("1/id 가 존재하지 않습니다");
	           		out2.flush();
	           	}
           	
           }
           else if (n == 1)
           {
	           	System.out.println( second + " "  + third  );
	           	Id = second;
	           	passwd = third;
	           	int confirm = dm.idExist(second);
	           	
	           	if(confirm == 1)
	           	{
	           		System.out.println("id 가 있어 아뒤 못만듭니다 ");
	           		out2.println("아이뒤를 변경해주세요 ");
	           		out2.flush();
	           	}
	           	else
	           	{
	           		out2.println("회원가입");
	           		out2.flush();
	           		System.out.println("id 없음 가입가능 ");
	           		dm.insertUserData(second, third);
	           	}
           	
           }
           else
           {
           		System.out.println("error");
           }
           
	   }
	   
	   public void login() throws IOException
	   {
		   int check = 0;
		   split(dis.readUTF());
		   
		   check = dm.matchingIdPasswd(second, third);
		   
		   if(check == 1)
			   out2.println("login complete");
		   else
			   out2.println("아이뒤와 비밀번호가 일치하지 않습니다.");
		   
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
