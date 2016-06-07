package hoyoung.myapplication;

import android.app.Activity;
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


public class MainActivity extends Activity implements View.OnClickListener {

    private mHandler aHandler;
    String read = "";

    String id_s;
    String uuid_s;
    String major_s;
    String minor_s;

    int port = 7777;

    EditText id;
    EditText uuid;
    EditText major;
    EditText minor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText)findViewById(R.id.editText0);

        uuid = (EditText)findViewById(R.id.editText1);

        major = (EditText)findViewById(R.id.editText2);

        minor = (EditText)findViewById(R.id.editText3);



        Button OK = (Button)findViewById(R.id.enroll);

        aHandler = new mHandler();



        OK.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.enroll :

                SocketThread rThread = new SocketThread();
                rThread.setDaemon(true);
                rThread.start();

                break;
        }
    }
    private class mHandler extends Handler {

        @Override
        public void handleMessage(Message msg){

            super.handleMessage(msg);

            switch (msg.what){
                case 0 :

                    Toast toast = Toast.makeText(MainActivity.this, read, Toast.LENGTH_SHORT);
                    toast.show();

                    break;
            }
        }
    }

    public class SocketThread extends Thread{

        public void run(){

            try {

                id_s = id.getText().toString();
                uuid_s = uuid.getText().toString();
                major_s = major.getText().toString();
                minor_s = minor.getText().toString();

                Socket socket;

                DataOutputStream output;


                socket = new Socket("20.20.3.47", port);

                output = new DataOutputStream(socket.getOutputStream());


                output.writeUTF("1");
                output.flush();
                output.writeUTF(id_s + "/" + uuid_s + "/" + major_s + "/" + minor_s + "/");
                output.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                read = reader.readLine();

                if(!read.equals("complete")){
                    aHandler.sendEmptyMessage(0);
                }else{
                    Toast toast = Toast.makeText(MainActivity.this, read , Toast.LENGTH_SHORT);
                    toast.show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
