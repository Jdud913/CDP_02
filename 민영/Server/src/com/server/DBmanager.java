package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

//import com.server.Server.FileServerClient;

public class DBmanager {
	String driver;
	String url;

	String id ;
	String pw ;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sqlcnt = null;
	StringBuffer sql = null;
	
	String id1 = "" ;
	String passwd1 = "";
	


	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}
	void startDatabase()
	{
		driver = "com.mysql.jdbc.Driver";
		url="jdbc:mysql://localhost/Ananpp";
		
		id = "root";
		pw = "aksen007";

		
		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("DB접속.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error");
		}		
	}
	
	
	public void insertUserData(String id,String passwd )
	{	
		try {

						
	            String stm = "INSERT INTO userdata" +
	            		" (id, passward) " +
	            		" VALUES(?,?)";
	            pstmt = conn.prepareStatement(stm);
	            
	            pstmt.setString(1, id);
	            pstmt.setString(2, passwd);
	            pstmt.executeUpdate();
	            
	            System.out.println("successful inserted");


			} catch (SQLException e) {

				System.out.println("something is wrong!");
				e.printStackTrace();
				return;

			}
		

		
	}
	

	public int idExist(String a )
	{
		int check  = 0 ;
		try
	    {
			if(conn == null)
				return -1;
			
			String query = "SELECT count(*) FROM userdata WHERE id = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,a);
			Statement st = conn.createStatement();		
			ResultSet rs = pstmt.executeQuery();
	                
	      while (rs.next())
	      {
	    	  check = rs.getInt(1);	        
	      }
	      
	      st.close();
	      return check;
	    }
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      
	      return -1;
	    }
		
	}
	
	public int matchingIdPasswd(String a ,String b)
	{
		int check  = 0 ;
		try
	    {
			if(conn == null)
				return -1;
			
			String query = "SELECT count(*) FROM userdata WHERE id = ? AND passwd = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,a);
			pstmt.setString(2,b);
			
			Statement st = conn.createStatement();
			
			
			ResultSet rs = pstmt.executeQuery();

	      while (rs.next())
	      {
	    	  check = rs.getInt(1);

	      }	      
	      st.close();
	      return check;
	    }
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      
	      return -1;
	    }
	  }

	
	
	public String getlocation(String id)
	{
		String location = "";
		
	try
    {
		if(conn == null)
			return null;
		
		String query = "SELECT link FROM userdata WHERE id = ? ";
		
		pstmt = conn.prepareStatement(query);
	      
	    pstmt.setString(1, id);
		
	    Statement st = conn.createStatement();
		ResultSet rs = pstmt.executeQuery();
                
      // iterate through the java resultset
      while (rs.next())
      {
    	  location = rs.getString("location");
        
        // print the results
        
      }
      
      st.close();
      return location;
    }
	catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
      
      return null;
    }
  }
	public void insertlocation(String id,String location )
	{	
		try {

						
	            String stm = "INSERT INTO location" +
	            		" (id, passward) " +
	            		" VALUES(?,?)";
	            pstmt = conn.prepareStatement(stm);
	            
	            pstmt.setString(1, id);
	            pstmt.setString(2, location);
	            pstmt.executeUpdate();
	            
	            System.out.println("successful inserted");


			} catch (SQLException e) {

				System.out.println("something is wrong!");
				e.printStackTrace();
				return;

			}
	}
	
	
	public String getmsg(String bid)
	{
		String msg = "";
		
	try
    {
		if(conn == null)
			return null;
		
		String query = "SELECT link FROM message WHERE bid = ? ";
		
		pstmt = conn.prepareStatement(query);
	      
	    pstmt.setString(1, bid);
		
	    Statement st = conn.createStatement();
		ResultSet rs = pstmt.executeQuery();
                
      // iterate through the java resultset
      while (rs.next())
      {
    	  msg = rs.getString("msg");
        
        // print the results
        
      }
      
      st.close();
      return msg;
    }
	catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
      
      return null;
    }
  }
	
}
	