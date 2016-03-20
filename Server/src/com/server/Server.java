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
    //DBmysql dm = new DBmysql();
 
    public static void main(String[] args) {
        // 5���� �����带 �����ϴ� ������ �����Ѵ�.
    	Server server = new Server(10);
        server.start();
    }
 
    public Server(int num) {
        try {
            
            serverSocket = new ServerSocket(8051);
            System.out.println(getTime() + " 준비중");
            
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
            	String phone = "";
            	String mom = "";
            	String flag = "";	
                System.out.println(getTime() + " 활성화 됨.");
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + " " + socket.getInetAddress()
                        + "접속을 했습니다");
                
                InputStream in = socket.getInputStream();    
                //OutputStream out = socket.getOutputStream();
                DataInputStream dis = new DataInputStream(in);
             	//DataOutputStream dos = new DataOutputStream(out); 
                
             	BufferedWriter networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             	PrintWriter out2 = new PrintWriter(networkWriter, true);
             	
                while(true)
                {
	
                int i = Integer.parseInt(dis.readUTF());
                System.out.println(i);
                
                switch(i)
                {
                case 0:
                {
                	BufferedOutputStream out1 = new BufferedOutputStream( socket.getOutputStream() );
                
                	String fileNameStr = dis.readUTF(); 
                    System.out.println("Id :"+fileNameStr);
                    FileInputStream fin = new FileInputStream(fileNameStr);
                    byte[] buffer = new byte[4096];
                    int bytesRead =0;
                    while ((bytesRead = fin.read(buffer)) > 0) {
                        out1.write(buffer, 0, bytesRead);
                    }
                    try {
             			Thread.sleep(1000);
             			}catch(InterruptedException e){
             				System.out.println(e.getMessage()); 
             			}
                    
                    out1.flush();
                    System.out.println("�Ϸ�");

                   fin.close();
                    out1.flush();
                    out1.close();

                	
                     break;
                }
                	
                case 1:
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
                         	phone = dm.getmyphone(Id);
                         	mom = dm.getlink(Id);
                         	flag = dm.getflag(Id);
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
                     	System.out.println( second + " "  + third + " "  + forth + " "  + fifth + " "  + sixth );
                     	Id = second;
                     	passwd = third;
                     	phone = forth;
                     	mom = fifth;
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
                     		dm.insertUserData(second, third, forth, fifth, sixth);
                     		dm.insertnavi(forth);
                     	}
                     	
                     }
                     else
                     {
                     	System.out.println("error");
                     }
                     
                     break;
                     
                }
                case 2:
                {
                	
                	String Id1 = dis.readUTF();
                	String myp = dm.getmyphone(Id1);
                	while(true)
                	{
                		//dis = new DataInputStream(in);
                		try {
                			Thread.sleep(500);
                		}catch(InterruptedException e){
                			System.out.println(e.getMessage()); //sleep �޼ҵ尡 �߻��ϴ� InterruptedException 
                		}
                		//System.out.println(phone);
                		dm.updatenavi(myp, dis.readUTF());
                		
                	}
                	
                	
                }
                	
                case 3:
                {
                	Id = dis.readUTF();
                	String link = dm.getlink(Id);
                	String coordinate = "";
                	String zzzz = "32323";
                	while(true)
                	{
                		 //dos = new DataOutputStream(out);
                         try {
                 			Thread.sleep(500);
                 			}catch(InterruptedException e){
                 				System.out.println(e.getMessage()); 
                 			}
                         coordinate = dm.getnavi(link);
                         //dos.writeUTF(coordinate);
                         
                         if(!coordinate.equals(zzzz))
                         {
                        	 out2.println(coordinate);
                        	 out2.flush();
                        	 zzzz =coordinate;
                         }
                         
                	}
                	
                }
                
                case 4:
                {
                	Id = dis.readUTF();
                	int availId = dm.idExist(Id);
                	
                	if(availId == 1)
                	{
                		out2.println("있음");
                		out2.flush();
                	}
                	else
                	{
                		out2.println("없음");
                		out2.flush();
                	}
                	
                }
               
               
                
                }
                
                if(i <0)
                	break;
                
                
                //in.close();
                //out.close();
               
               
               
                }
               
                //dis.close();
                //dos.close();
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
/*
class FileSender extends Thread {
    Socket socket;
    DataOutputStream dos;
    FileInputStream fis;
    BufferedInputStream bis;
 
    public FileSender(Socket socket) {
        this.socket = socket;
        try {
            // ������ ���ۿ� ��Ʈ�� ����
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void run() {
        try {
            System.out.println("���� ���� �۾��� �����մϴ�.");
 
            // ���� �̸� ����
            // String fName = "�۾���a.txt";
            // String fName = "��Ƽa.ppt";
            // String fName = "�۾���a.jpg";
            String fName = "test.mp3";
            dos.writeUTF(fName);
            System.out.printf("���� �̸�(%s)�� �����Ͽ����ϴ�.\n", fName);
 
            // ���� ������ �����鼭 ����
            File f = new File(fName);
            fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);
 
            int len;
            int size = 4096;
            byte[] data = new byte[size];
            while ((len = bis.read(data)) != -1) {
                dos.write(data, 0, len);
            }
 
            dos.flush();
            dos.close();
            bis.close();
            fis.close();
            System.out.println("���� ���� �۾��� �Ϸ��Ͽ����ϴ�.");
            System.out.println("���� ������ ������ : " + f.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/








/////////////////////////////////////////////////////////////////////////////////
/*
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Server implements Runnable{
	
	private final int PORT = 8051;
	
	ServerSocket s = null;
	
	String first = "";
	String second = "";
	String third = "";
	String forth = "";
	String fifth = "";
	String sixth = "";
	
	
	public static void main(String[] args) {
	
    	new Thread(new Server() ).start();
    	DBmanager dm = new DBmanager();
    	dm.startDatabase();
    	
    	run();
	
	}

    
    
    public void run() {
        
        try {
            s = new ServerSocket(PORT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connecting......");
        while (s != null) {
            try {
                Socket client = s.accept();
                System.out.println("client = " + client.getInetAddress());
                new Thread( new FileServerClient(client) ).start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void listen() throws IOException
    {
    	 while (true) {
             Socket client = s.accept();
             System.out.println("S: Receiving...");
             try {
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 String str = in.readLine();
                 split(str);
                  
                 //PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                // out.println("Server Received " + str);
             } catch (Exception e) {
                 System.out.println("S: Error");
                 e.printStackTrace();
             } finally {
                 client.close();
                 System.out.println("S: Done.");
             }
         }
    }
    
    public void split(String buffer)
    {
    	
    	 first = buffer.split("/")[0];
    	
    	if(first.equals("0"))
    	{
    		 second = buffer.split("/")[1];
    		 third  = buffer.split("/")[2];
    	}
    	else if(first.equals("1"))
    	{
    		 second = buffer.split("/")[1];
    		 third  = buffer.split("/")[2];
    		 forth  = buffer.split("/")[3];
    		 fifth  = buffer.split("/")[4];
    		 sixth  = buffer.split("/")[5];

    	}
    }

    static class FileServerClient implements Runnable {
        private Socket socket;

        FileServerClient( Socket s) {
            socket = s;
        }

        public void run() {
            try {
                BufferedOutputStream out = new BufferedOutputStream( socket.getOutputStream() );
                FileInputStream fileIn = new FileInputStream( "C:/Users/USER/Desktop/test.mp3");
                byte[] buffer = new byte[10000];
                int bytesRead =0;
                while ((bytesRead = fileIn.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
                out.close();
                fileIn.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    
    
    static class FileServerClient implements Runnable {
        private Socket socket;

        FileServerClient( Socket s) {
            socket = s;
        }

        public void run() {
            try {
                BufferedOutputStream out = new BufferedOutputStream( socket.getOutputStream() );
                FileInputStream fileIn = new FileInputStream( "C:/Users/USER/Desktop/test.mp3");
                byte[] buffer = new byte[10000];
                int bytesRead =0;
                while ((bytesRead = fileIn.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
                out.close();
                fileIn.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
} */



