package commie.com.example.septembernine.commie01.commie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import commie.com.example.septembernine.commie01.R;
public class Commie_Login extends Activity {

    String Id = "";
    String pw = "";
    String First ="" ;
    String flag = "";

    private String html = "";

    //private  Handler mHandler;
    TextHandler handler;
    private Socket socket;
    private BufferedReader networkReader;

    MainThread thread;
    OutputStream out;
    DataOutputStream dos;

    private String ip = "20.20.3.47"; // IP
    private int port = 7777; // PORT번호

    // 액티비티넘어갈 때마다 죽이고 새로 살리고
//    @Override
//    protected void onStop() {
//        super.onStop();
//        try {
//            socket.close();
//            if(thread != null && thread.isAlive())
//                thread.interrupt();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_commie__login);

        Button bt_OK =(Button)findViewById(R.id.bt_OK);
        Button bt_Join =(Button)findViewById(R.id.bt_Join);
        final EditText et = (EditText)findViewById(R.id.login_id);
        final EditText et1 = (EditText)findViewById(R.id.login_pwd);

        handler = new TextHandler();

        if(thread != null && thread.isAlive())
            thread.interrupt();

        thread = new MainThread();
        thread.setDaemon(true);
        thread.start();

        bt_OK.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
//
//                Intent i = new Intent(Commie_Login.this, Commie_Beacon01.class);
//                startActivity(i);
//                finish();

                if(!et.getText().toString().equals("") && !et1.getText().toString().equals("") ) {

                    Id = et.getText().toString();
                    pw = et1.getText().toString();

                    String sum = (Id + "/" + pw);

                    try {
                        dos.writeUTF("2");
                        dos.flush();
                        dos.writeUTF(sum);
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(Commie_Login.this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show();
                }

           }
        });

        bt_Join.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Commie_Login.this, Commie_Join.class);
                startActivity(i);
                finish();
            }
        });
    }



    class TextHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            html = bundle.getString("text");
            //tv5.setText(html);

            First = html.split("/")[0];
            flag = html.split("/")[1];
        //  Toast.makeText(Commie_Login.this, flag, Toast.LENGTH_SHORT).show();

            if(First.equals("1"))
            {

                //자식으로 가는 화면 넘기기
                if (flag.equals("4"))
                {
                    try {
                        socket.close();
                        if(thread != null && thread.isAlive())
                            thread.interrupt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(Commie_Login.this, Commie_Beacon.class);
                    // id값 넘겨주기
                    i.putExtra("id_u",Id);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(Commie_Login.this, "아이디와 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void setSocket(String ip, int port) throws IOException {

        try {
            socket = new Socket(ip, port);
            //networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //in = socket.getInputStream();
            out = socket.getOutputStream();
            //dis = new DataInputStream(in);
            dos = new DataOutputStream(out);
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }


    class MainThread extends  Thread{

        public void run()
        {
            try{
                setSocket(ip,port);

                String line = null;
                Log.w("ChattingStart", "Start Thread");

                while (!MainThread.interrupted()) {
                    Log.w("Chatting is running", "chatting is running");
                    //networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    line = networkReader.readLine();//dis.readUTF();

                    //line = dis.readUTF();
                    Log.w("send message", "please");
                    //html = line;

                    sendText(line);
                }
            }catch ( IOException e1)
            {
                e1.printStackTrace();
            }catch (Exception e) {
                String err = e.getMessage();
                Log.w("error message", err);
            }
        }


        private void sendText(String text){
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("text", text);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }


}

