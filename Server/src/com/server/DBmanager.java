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
	
	
	//ȸ������ �߰�(id passwd myphone link flag)
	public void insertUserData(String id,String passwd ,String myphon,String link,String flag)
	{	
		try {

			//conn = DriverManager.getConnection(
			//		"jdbc:postgresql://127.0.0.1:5432/UNS222", "postgres",
			//			"Userpassword");
						
	            String stm = "INSERT INTO userdata" +
	            		" (id, passwd, myphone,link, flag) " +
	            		" VALUES(?,?, ?,?, ?)";
	            pstmt = conn.prepareStatement(stm);
	            
	            pstmt.setString(1, id);
	            pstmt.setString(2, passwd);
	            pstmt.setString(3, myphon);
	            pstmt.setString(4, link);
	            pstmt.setString(5, flag);
	            pstmt.executeUpdate();
	            
	            System.out.println("successful inserted");


			} catch (SQLException e) {

				System.out.println("something is wrong!");
				e.printStackTrace();
				return;

			}
		

		
	}
	
	public void insertnavi(String myphon)
	{	
		try {
	            String stm = "INSERT INTO navi" +
	            		" (id) " +
	            		" VALUES(?)";
	            pstmt = conn.prepareStatement(stm);
	            
	            pstmt.setString(1, myphon );
	            pstmt.executeUpdate();
	            
	            System.out.println("successful inserted");


			} catch (SQLException e) {

				System.out.println("something is wrong!");
				e.printStackTrace();
				return;

			}
	
	}
	
	void updatenavi(String myphone, String coordinate)
	{
		try {
		 String query = "update navi set navi = ? where id = ?";
		 pstmt = conn.prepareStatement(query);
		 pstmt.setString (1, coordinate);
		 pstmt.setString(2, myphone);
	 
	      // execute the java preparedstatement
		 pstmt.executeUpdate();
		} catch (SQLException e) {

			System.out.println("something is wrong!");
			e.printStackTrace();
			return;

		}
	}
	
	
	public void close()
	{
		try {

			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (SQLException ex) {
			System.out.println("can't close");
			ex.printStackTrace();
		}
	}
	//id �ߺ�ýũ
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
	                
	      // iterate through the java resultset
	      while (rs.next())
	      {
	    	  check = rs.getInt(1);
	        
	        // print the results
	        
	      }
	      
	      st.close();
	      //st.close();
	      return check;
	    }
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      
	      return -1;
	    }
		
	}
	public int phoneExist(String a )
	{
		int check  = 0 ;
		try
	    {
			if(conn == null)
				return -1;
			
			String query = "SELECT count(*) FROM userdata WHERE myphone = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,a);
			
			Statement st = conn.createStatement();
			
			
			ResultSet rs = pstmt.executeQuery();
	                
	      // iterate through the java resultset
	      while (rs.next())
	      {
	    	  check = rs.getInt(1);
	        
	        // print the results
	        
	      }
	      
	      st.close();
	      //st.close();
	      return check;
	    }
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      
	      return -1;
	    }
		
	}
	public int linkExist(String a )
	{
		int check  = 0 ;
		try
	    {
			if(conn == null)
				return -1;
			
			String query = "SELECT count(*) FROM userdata WHERE link = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,a);
			
			Statement st = conn.createStatement();
			
			
			ResultSet rs = pstmt.executeQuery();
	                
	      // iterate through the java resultset
	      while (rs.next())
	      {
	    	  check = rs.getInt(1);
	        
	        // print the results
	        
	      }
	      
	      st.close();
	      //st.close();
	      return check;
	    }
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      
	      return -1;
	    }
		
	}
	//id passwd Ȯ��
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
	                
	      // iterate through the java resultset
	      while (rs.next())
	      {
	    	  check = rs.getInt(1);
	        
	        // print the results
	        
	      }
	      
	      st.close();
	      //st.close();
	      return check;
	    }
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      
	      return -1;
	    }
	  }
	
	// link ��ȣ �������� 
	public String getlink(String id)
	{
		String link = "";
		
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
    	  link = rs.getString("link");
        
        // print the results
        
      }
      
      st.close();
      return link;
    }
	catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
      
      return null;
    }
  }
	
	
	public String getmyphone(String id)
	{
		String link = "";
		
	try
    {
		if(conn == null)
			return null;
		
		String query = "SELECT myphone FROM userdata WHERE id = ? ";
		
		pstmt = conn.prepareStatement(query);
	      
	    pstmt.setString(1, id);
		
	    Statement st = conn.createStatement();
		ResultSet rs = pstmt.executeQuery();
                
      // iterate through the java resultset
      while (rs.next())
      {
    	  link = rs.getString("myphone");
        
        // print the results
        
      }
      
      st.close();
      return link;
    }catch (Exception e)
    {
        System.err.println("Got an exception! ");
        System.err.println(e.getMessage());
        
        return null;
      }
    }
	
	public String getflag(String id)
	{
		String link = "";
		
	try
    {
		if(conn == null)
			return null;
		
		String query = "SELECT flag FROM userdata WHERE id = ? ";
		
		pstmt = conn.prepareStatement(query);
	      
	    pstmt.setString(1, id);
		
	    Statement st = conn.createStatement();
		ResultSet rs = pstmt.executeQuery();
                
      // iterate through the java resultset
      while (rs.next())
      {
    	  link = rs.getString("flag");
        
        // print the results
        
      }
      
      st.close();
      return link;
    }catch (Exception e)
    {
        System.err.println("Got an exception! ");
        System.err.println(e.getMessage());
        
        return null;
      }
    }
	
	
	public String getnavi(String id)
	{
		String navi = "";
		
	try
    {
		if(conn == null)
			return null;
		
		String query = "SELECT navi FROM navi WHERE id = ? ";
		
		pstmt = conn.prepareStatement(query);
	      
	    pstmt.setString(1, id);
		
	    Statement st = conn.createStatement();
		ResultSet rs = pstmt.executeQuery();
                
      // iterate through the java resultset
      while (rs.next())
      {
    	  navi = rs.getString("navi");
        
        // print the results
        
      }
      
      st.close();
      return navi;
    }
	catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
      
      return null;
    }
  }
	
}
	