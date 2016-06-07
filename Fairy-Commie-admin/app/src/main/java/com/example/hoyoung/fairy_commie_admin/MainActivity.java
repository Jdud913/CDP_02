package com.example.hoyoung.fairy_commie_admin;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //GoogleMap 객체
    private GoogleMap mGoogleMap;
    //먼저 현재 위치 정보를 가져오기 위한 GpsInfo 클래스
    private GpsInfo gps;

    List<String> u_id;
    List<String> u_longti;
    List<String> u_lati;

    String u_id1;
    String u_longti1;
    String u_lati1;

    double longti;
    double lati;

    String read = "";
    String ip = "20.20.3.47";
    int port = 7777;

    private static final String TAG = "LogTest";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w("TAG", "2");

        // BitmapDescriptorFactory 생성하기 위한 소스
        MapsInitializer.initialize(getApplicationContext());
        init();

        u_id1 = "";
        u_longti1 = "";
        u_lati1 = "";
        u_id = new ArrayList<String>();
        u_longti = new ArrayList<String>();
        u_lati = new ArrayList<String>();

        Button bt_renew = (Button) findViewById(R.id.btn_renew);
        Button bt_Add = (Button) findViewById(R.id.btn_AddBeacon);

        bt_renew.setOnClickListener(this);
        bt_Add.setOnClickListener(this);

    }

    //Google Map 화면의 초기화
    public void clearMarker() {

        mGoogleMap.clear();

//        LatLng loc = new LatLng(gps.getLatitude(), gps.getLongitude());
//        MarkerOptions marker = new MarkerOptions().position(loc);
//        marker.title("현재위치");
//        mGoogleMap.addMarker(marker);
    }

    //사용자의 id와 위도, 경도 값을 받아와 구글 맵 위에 marker로 찍어준다.
    public void addMarker(String uid, double x, double y) {

        LatLng loc = new LatLng(x, y);
        MarkerOptions marker = new MarkerOptions().position(loc);
        marker.title(uid);

        mGoogleMap.addMarker(marker);

    }


    /**
     * 초기화
     *
     * @author
     */
    private void init() {

        //현재 MainActivity 화면에 띄우기 위해 Fragment 활용
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.this);
        mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        //GpsInfo 객체의 생성
        gps = new GpsInfo(MainActivity.this);

        // isGetLocation() 함수로 제대로 가져왔는지 판단한다.
        if (gps.isGetLocation()) {
            //위도와 경도를 가져온다.
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);


            // 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();


            // Showing the current location in Google Map
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            // Map 을 zoom 합니다.
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
            
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //Button 클릭 시 서버에서 사용자들의 최신 위치를 받아와 지도에 출력
            case R.id.btn_renew:

                clearMarker();

                //서버와의 통신을 위한 쓰레드를 생성 및 시작한다.
                ReceiveThread rThread = new ReceiveThread();
                rThread.start();

                //서버로부터 받은 사용자의 아이디와 위치를 지도위에 출력한다.
                for (int i = 0; i < u_id.size(); i++) {

                    lati = Double.valueOf(u_longti.get(i)).doubleValue();
                    longti = Double.valueOf(u_lati.get(i)).doubleValue();
                    addMarker(u_id.get(i), lati, longti);

                }

                u_id.clear();
                u_longti.clear();
                u_lati.clear();

                break;
        }
    }


    public class ReceiveThread extends Thread {

        public void run() {
            try {
                Socket socket;
                DataOutputStream output;

                //socket 객체를 생성하여 ip주소와 port 번호를 넘겨 서버와 연결한다.
                socket = new Socket(ip, port);

                //DataOutputStream을 통해 Data를 전송한다.
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF("4");
                output.flush();

                //서버로부터 사용자들의 위치를 전송받기 위한 BufferedReader를 생성한다.
                BufferedReader reader = new BufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));

                while (true) {
                    //서버로부터 메시지를 받는다.
                    read = reader.readLine();

                    //서버로부터 받은 메시지가 end라면 메시지를 받기위해 대기하는 것을 중지한다.
                    if (read.equals("end")) {
                        break;
                    }

                    //서버로로부터 받은 메시지가 end가 아닐 경우 전달받은 메시지를 분리하여 String List에 각각에 저장한다.
                    split(read);
                    u_id.add(u_id1);
                    u_longti.add(u_longti1);
                    u_lati.add(u_lati1);
                }

                socket.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    //buffer에 담긴 String을 / 기호를 기준으로 분리하여 저장한다.
    public void split(String buffer) {

        u_id1 = buffer.split("/")[0];
        u_longti1 = buffer.split("/")[1];
        u_lati1 = buffer.split("/")[2];

    }
}

//    public class SendThread extends Thread{
//
//        public void run(){
//            try {
//                Socket socket;
//
//                DataOutputStream output_mac;
//                DataOutputStream output_major;
//                DataOutputStream output_minor;
//
//                socket = new Socket("192.168.0.2", port);
//
//                output_mac = new DataOutputStream(socket.getOutputStream());
//                output_major = new DataOutputStream(socket.getOutputStream());
//                output_minor = new DataOutputStream(socket.getOutputStream());
//
//
//                output_mac.writeUTF(mac_s);
//                output_major.writeUTF(major_s);
//                output_minor.writeUTF(minor_s);
//
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//
//
//        }
//
//    }


//Thread에서 보낸 sendEmptyMessage의 id 값으로 각각의 스레드별 핸들러를 지정
//    private class mHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg){
//            super.handleMessage(msg);
//
//            Context mContext = getApplicationContext();
//            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//            layout = inflater.inflate(R.layout.custom_dialog, (ViewGroup)findViewById(R.id.layout_root));
//
//            switch (msg.what){
//                case 0 :
//
//                    AlertDialog ad;
//                    final AlertDialog.Builder aDialog;
//                    aDialog = new AlertDialog.Builder(MainActivity.this);
//                    aDialog.setTitle("비콘추가");
//                    aDialog.setView(layout);
//
//
//
//                    final EditText mac = (EditText) layout.findViewById(R.id.mac);
//                    final EditText major = (EditText) layout.findViewById(R.id.major);
//                    final EditText minor = (EditText) layout.findViewById(R.id.minor);
//
//                    aDialog.setPositiveButton("추가", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                            mac_s = mac.getText().toString();
//                            major_s = major.getText().toString();
//                            minor_s = minor.getText().toString();
//
//                            dialog.dismiss();
//
//                            SendThread sendThread = new SendThread();
//                            sendThread.start();
//
//                        }
//                    });
//                    aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    ad = aDialog.create();
//                    ad.show();
//
//
//
//                    break;
//
//                case 1 :
//                    break;
//            }
//        }
//    }

    //UI에 관한 것은 메인스레드에서 진행하거나 스레드에서 Handler를 호출하여 작업할 수 있다.
    //여기서는 sub thread를 생성하여 핸들러를 통해 alertDialog 작업을 수행하게 한다
//    public class AlertThread extends Thread{
//
//        public void run(){
//
//            try {
//                aHandler.sendEmptyMessage(0);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }

