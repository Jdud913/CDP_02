package client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
 
 
public class client {
    public static void main(String[] args) {
    	
    	
        try{
            String serverIp = args[0];
            //long startTime = System.currentTimeMillis();
            //System.out.println("Start time: " + new Date());

            
            
            
            // ������ �����Ͽ� ������ ��û�Ѵ�.
            System.out.println("������ �������Դϴ�. ����IP : " + serverIp);
            Socket socket = new Socket(serverIp, 8051);
            
            
            // ������ �Է½�Ʈ���� ��´�.
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
             
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            
            BufferedReader networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            

            dos.writeUTF("1");

            dos.writeUTF("1/pk77703/aaaaa/010-3007-3694/010-3007-2222/3");
           // System.out.println( " �����͸� �����Ͽ����ϴ�.");
            
            //dos.writeUTF("0/pk77703/aaaaa");
           //System.out.println( " �����͸� �����Ͽ����ϴ�.");
            
            // �������κ��� ���� �����͸� ����Ѵ�.
            System.out.println("�����κ��� ���� �޼��� : " + networkReader.readLine());
            

            
            String s = ""; 
            dos.writeUTF("2");
            int count = 1;
            while(true)
            {
            	if(count == 100)
            		break;
            	
            	s = Integer.toString(count);
            	
            	dos.writeUTF(s);
            	
            	 Thread.sleep(500);
            	count++;
            	
            }

         
             
            
            dos.close();
            dis.close();
            socket.close();
            System.out.println("������ ����Ǿ����ϴ�.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}



