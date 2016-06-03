package com.example.hoyoung.fairy_commie_admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by HoYoung on 2016-05-13.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private mHandler aHandler;
    String read = "";
    EditText id;
    EditText pw;

    int port = 7777;
    String ip = "20.20.3.46";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText)findViewById(R.id.login_id);
        pw = (EditText)findViewById(R.id.login_pwd);
        Button bt_login = (Button)findViewById(R.id.bt_Login);

        bt_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Login:


                SocketThread rThread = new SocketThread();
                rThread.setDaemon(true);
                rThread.start();

                break;

        }
    }

    public class SocketThread extends Thread{

        public void run(){

            try {

                String login_id = id.getText().toString();
                String login_pw = pw.getText().toString();


                Socket socket;

                DataOutputStream output;


                socket = new Socket("20.20.3.47", port);

                output = new DataOutputStream(socket.getOutputStream());


                output.writeUTF("2");
                output.flush();
                output.writeUTF(login_id + "/" + login_pw + "/");
                output.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                read = reader.readLine();

                if(!read.equals("complete")){
                    aHandler.sendEmptyMessage(0);
                }else{
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private class mHandler extends Handler {

        @Override
        public void handleMessage(Message msg){

            super.handleMessage(msg);

            switch (msg.what){
                case 0 :

                    Toast toast = Toast.makeText(LoginActivity.this, read, Toast.LENGTH_SHORT);
                    toast.show();

                    break;
                case 1 :

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    break;
            }
        }
    }
}
