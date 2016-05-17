package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

	ServerSocket serverSocket = null;//서버용 대기 소켓
	Thread[] threadArr;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Server server = new Server(1);
		server.start();
		
	}
	
	public Server(int num){
		try{
			//서버 소켓을 생성하여 해당포트와 바인딩
			serverSocket = new ServerSocket(7777);
			threadArr = new Thread[num];
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void start(){
		for(int i=0; i<threadArr.length; i++){
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}

	@Override
	public void run() {
		
		while(true){
			
			int count = 0;
			
			try {
				System.out.println("server가 연결 요청을 기다립니다.");
				Socket socket = serverSocket.accept();
				System.out.println("" + socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");
				
				String[] beacon = new String[3]; 
				
				InputStream in = socket.getInputStream();
//				OutputStream out = socket.getOutputStream();
				
				DataInputStream dataInputStream = new DataInputStream(in);
//				DataOutputStream dataOutputStream = new DataOutputStream(out);
				
				
				
				while(true){
					
					if(count == 3){
						System.out.println("mac : " +  beacon[0] + ", major : " + beacon[1] + ", minor : " + beacon[2]);
						dataInputStream.close();
						socket.close();
						count = 0;
						break;
					}
					
					String input = dataInputStream.readUTF();
						
//					System.out.println("클라이언트로부터 받은 메세지 : " + input);	
					
					if(count == 0){
						beacon[0] = input;
					}else if(count == 1){
						beacon[1] = input;
					}else if(count == 2){
						beacon[2] = input;
					}
					
					count++;
				}
							
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //데이터 교환 소켓			
		}
		
		
	}

}
