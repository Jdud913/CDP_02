package client;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
 
 
public class parent {
    public static void main(String[] args) {
    	
    	
        try{
            String serverIp = "20.20.2.116";
            //long startTime = System.currentTimeMillis();
            //System.out.println("Start time: " + new Date());
      
            // ������ �����Ͽ� ������ ��û�Ѵ�.
            System.out.println("������ �������Դϴ�. ����IP : " + serverIp);
            Socket socket = new Socket(serverIp, 7777);
            
            
            // ������ �Է½�Ʈ���� ��´�.
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
             
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);

            

            dos.writeUTF("2");
            dos.flush();
            System.out.println( " �����͸� �����Ͽ����ϴ�.");
            
            dos.writeUTF("pk77703");
            dos.flush();
            dos.writeUTF("aaaaa");
            dos.flush();
            
            System.out.println(dis.readUTF());
            
            dos.writeUTF("1");
            dos.flush();
            int i = 1;
        
            
            System.out.println(dis.readUTF());
            System.out.println(dis.readUTF());
          
            dos.writeUTF("4");
            dos.flush();
            
            while(dis.readUTF() != null)
            	System.out.println(dis.readUTF());
           
            
            
            
            
            
            
            
            
            
            

            dis.close();
            dos.close();
            socket.close();
            System.out.println("������ ����Ǿ����ϴ�.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}



