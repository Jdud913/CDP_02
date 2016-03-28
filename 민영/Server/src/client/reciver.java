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
 
 
public class reciver {
    public static void main(String[] args) {
    	
    	
        try{
            String serverIp = args[0];
            //long startTime = System.currentTimeMillis();
            //System.out.println("Start time: " + new Date());
      
            // 소켓을 생성하여 연결을 요청한다.
            System.out.println("서버에 연결중입니다. 서버IP : " + serverIp);
            Socket socket = new Socket(serverIp, 8051);
            
            
            // 소켓의 입력스트림을 얻는다.
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
             
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);

            

            dos.writeUTF("0");
            System.out.println( " 데이터를 전송하였습니다.");
            
            FileReceiver fr = new FileReceiver(socket);
            fr.start();
            
            

            dis.close();
            dos.close();
            socket.close();
            System.out.println("연결이 종료되었습니다.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class FileReceiver extends Thread {
    Socket socket;
    DataInputStream dis;
    FileOutputStream fos;
    BufferedOutputStream bos;
 
    public FileReceiver(Socket socket) {
        this.socket = socket;
    }
 
    @Override
    public void run() {
        try {
            System.out.println("파일 수신 작업을 시작합니다.");
            dis = new DataInputStream(socket.getInputStream());
 
            // 파일명을 전송 받고 파일명 수정.
            String fName = dis.readUTF();
            System.out.println("파일명 " + fName + "을 전송받았습니다.");
            fName = fName.replaceAll("a", "b");
 
            // 파일을 생성하고 파일에 대한 출력 스트림 생성
            File f = new File(fName);
            fos = new FileOutputStream(f);
            bos = new BufferedOutputStream(fos);
            System.out.println(fName + "파일을 생성하였습니다.");
 
            // 바이트 데이터를 전송받으면서 기록
            int len;
            int size = 4096;
            byte[] data = new byte[size];
            while ((len = dis.read(data)) != -1) {
                bos.write(data, 0, len);
            }
 
            bos.flush();
            bos.close();
            fos.close();
            dis.close();
            System.out.println("파일 수신 작업을 완료하였습니다.");
            System.out.println("받은 파일의 사이즈 : " + f.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


