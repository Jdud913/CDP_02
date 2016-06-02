package commie.com.example.septembernine.commie01.commie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import commie.com.example.septembernine.commie01.R;

public class Commie_Beacon extends Activity implements RECOServiceConnectListener, RECORangingListener {
    private static final String TAG = "DD";

    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;

    List<String> uid;
    List<String> uuid;
    List<String> major;
    List<String> minor;

    int z =-1;
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    public static final boolean SCAN_RECO_ONLY = true;
    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true;
    public static final boolean DISCONTINUOUS_SCAN = false;


    double[] Accuracy = {9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999};
    int[] rssi_b = {9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999};
    int[] tx_Power = {9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999};

    int[] check_index = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int[] On_or_Off = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int index = -1;
    double temp;

    String resName;
    String packName;
    int resID;

    private String html = "";

    TextHandler handler;
    private Socket socket;
    private BufferedReader networkReader;
    MainThread thread;
    OutputStream out  ;
    DataOutputStream dos  ;
 //   String id;

    int t = 1;
    private String ip = "20.20.3.47"; // IP
    private int port = 7777; // PORT번호

    private GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_commie_beacon);
        Intent i = getIntent();
     //   id = i.getExtras().getString("id_u");

        //////////
        uid = new ArrayList<String>();
        uuid = new ArrayList<String>();
        major = new ArrayList<String>();
        minor = new ArrayList<String>();
        //////////

        handler = new TextHandler();
        Log.d("sequen :" , "1");

        if(thread != null && thread.isAlive())
            thread.interrupt();
        Log.d("sequen :" , "2");

        thread = new MainThread();
        thread.setDaemon(true);
        thread.start();

        Log.d("sequen :", "5");
        packName = this.getPackageName();

        //beacon감지 시작
        recoManager = RECOBeaconManager.getInstance(this, true, true);
        recoManager.bind(this);
        recoManager.setRangingListener(this);

    }

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

//  연결이 되었을때 제공되는 서비스 부분
    @Override
    public void onServiceConnect() {
//  리스트 생성해서 비콘 값 저장
        rangingRegions = new ArrayList<RECOBeaconRegion>();
//  비콘의UUID, Major, 이름
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
        for(int i=0; i<uuid.size(); i++) {
            rangingRegions.add(new RECOBeaconRegion(uuid.get(i), Integer.parseInt(major.get(i)), Integer.parseInt(minor.get(i)), Integer.toString(i)));
        }
//        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1979, "0"));
//        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1984, "1"));
//        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1987, "2"));
//        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 1995, "3"));


        for (RECOBeaconRegion region : rangingRegions) {
            try {
                recoManager.startRangingBeaconsInRegion(region);
                recoManager.requestStateForRegion(region);
            } catch (RemoteException e) {
                // RemoteException 발생 시 작성 코드
            } catch (NullPointerException e) {
                // NullPointerException 발생 시 작성 코드
            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode arg0) {
        // TODO Auto-generated method stub
    }


    //1초마다 비콘변화 감지
    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> arg0, RECOBeaconRegion arg1) {
        // TODO Auto-generated method stub
        z =-1;
        for (RECOBeacon r_beacon : arg0) {
            //우리 비콘인지 확인
            z++;
            if(arg1.getMinor() == Integer.parseInt(minor.get(z))) {
                index = z;
            //    Toast.makeText(Commie_Beacon.this, " " + minor.get(z), Toast.LENGTH_SHORT).show();
            }

//            if (arg1.getMinor() == 1979/*Integer.parseInt(minor.get(0))*/) {
//                index = 0;
//            }else if (arg1.getMinor() == 1984) {
//                index = 1;
//            }else if (arg1.getMinor() == 1987) {
//                index = 2;
//            }else if (arg1.getMinor() == 1995) {
//                index = 3;
//            }

//       Toast.makeText(Commie_Beacon.this, uuid.get(0)+" "+major.get(0)+" "+minor.get(0), Toast.LENGTH_SHORT).show();
    //     Toast.makeText(Commie_Beacon.this, " "+index, Toast.LENGTH_SHORT).show();

//            if(index == 3){
//                Toast.makeText(Commie_Beacon.this, "위험지역"+index+"무시무시한 415호 지역입니다", Toast.LENGTH_SHORT).show();
//            }

            if (index != -1) {
                rssi_b[index]   = r_beacon.getRssi();
                tx_Power[index] = r_beacon.getTxPower();
                Accuracy[index] = r_beacon.getAccuracy();

                temp = calculateDistance(tx_Power[index], rssi_b[index]);

                if(temp == -1){
                    temp = Accuracy[index];
                }

                if( temp < 3 && index == z ){
                    check_index[index]++;
                    if (check_index[index] == z) {
                        if (On_or_Off[index] == -1) {
                            gps = new GpsInfo(Commie_Beacon.this);
                            if (gps.isGetLocation()) {
                                double latitude = gps.getLatitude();
                                double longitude = gps.getLongitude();
                                Toast.makeText(Commie_Beacon.this, " " + minor.get(z), Toast.LENGTH_SHORT).show();
                                String mygps = latitude+"/"+longitude+"/";
                                try {
                                    dos.writeUTF("3");
                                    dos.flush();
                                    dos.writeUTF(minor.get(z));
                                    dos.flush();
                                    dos.writeUTF(mygps);
                                    dos.flush();
                                } catch (IOException e) {
                                e.printStackTrace();
                                }
                            } else {
                                // GPS
                                gps.showSettingsAlert();
                            }
                            On_or_Off[index] = 0;
                            index = -1;
                        }
                  }
                }
            }

        }
    }

    protected static double calculateDistance(int txPower, int rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine distance, return -1.
        }
        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        }else{
            return -1.0;
        }
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion arg0, RECOErrorCode arg1) {
        // TODO Auto-generated method stub
    }

    class TextHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            html = bundle.getString("text");
            //  tv5.setText(html);
        }
    }

//    public void Thread(){
//        Runnable task = new Runnable(){
//            public void run(){
//                /**
//                 * while문을 돌려서 음악이 실행중일때 게속 돌아가게 합니다
//                 */
//                while(music.isPlaying()){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        Thread thread = new Thread(task);
//        thread.start();
//    }

//    public void set_resouce(int index){
//        resName = "@raw/m" + index;
//        resID = getResources().getIdentifier(resName, "raw", packName);
//    }

    public void setSocket(String ip, int port) throws IOException {
        try {
            socket = new Socket(ip, port);
            networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = socket.getOutputStream();
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
                setSocket(ip, port);
                String line = null;
                Log.w("ChattingStart", "Start Thread");

                dos.writeUTF("5");
                dos.flush();
//                dos.writeUTF(id);
//                dos.flush();

                while (!MainThread.interrupted()) {
                    Log.w("Chatting is running", "chatting is running");
                    //networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    line = networkReader.readLine();//dis.readUTF();

                    uid.add(line.split("/")[0]);
                    uuid.add(line.split("/")[1]);
                    major.add(line.split("/")[2]);
                    minor.add(line.split("/")[3]);

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