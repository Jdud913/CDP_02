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

    private GoogleMap mGoogleMap;
    private GpsInfo gps;
//    private mHandler aHandler;

    List<String> second;
    List<String> third;
    List<String> fourth;

    String second1;
    String third1;
    String fourth1;

    String mac_s;
    String major_s;
    String minor_s;

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

        //핸들러 객체 생성
//        aHandler = new mHandler();

        // BitmapDescriptorFactory 생성하기 위한 소스
        MapsInitializer.initialize(getApplicationContext());
        init();

        second1 = "";
        third1 = "";
        fourth1 = "";
        second = new ArrayList<String>();
        third = new ArrayList<String>();
        fourth = new ArrayList<String>();

        Button bt_renew = (Button) findViewById(R.id.btn_renew);
        Button bt_Add = (Button) findViewById(R.id.btn_AddBeacon);

        bt_renew.setOnClickListener(this);
        bt_Add.setOnClickListener(this);

    }


    public void clearMarker() {

        mGoogleMap.clear();

//        LatLng loc = new LatLng(gps.getLatitude(), gps.getLongitude());
//        MarkerOptions marker = new MarkerOptions().position(loc);
//        marker.title("현재위치");
//        mGoogleMap.addMarker(marker);
    }

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

        GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.this);
        mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        // 맵의 이동
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        gps = new GpsInfo(MainActivity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
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


            //mGoogleMap.addMarker(optFirst).showInfoWindow();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //Button 클릭 시 서버에서 사용자들의 최신 위치를 받아와 지도에 출력
            case R.id.btn_renew:

                clearMarker();

                ReceiveThread rThread = new ReceiveThread();
                rThread.start();

                for (int i = 0; i < second.size(); i++) {
//                    if(third.get(i) != null) {

                        lati = Double.valueOf(third.get(i)).doubleValue();
                        longti = Double.valueOf(fourth.get(i)).doubleValue();
                        addMarker(second.get(i), lati, longti);

//                    }
                }

                second.clear();
                third.clear();
                fourth.clear();

                break;

            //AlertDialog를 사용하여 서버에 비콘을 추가 등록하기 위한 버튼
//            case R.id.btn_AddBeacon :
//
//                AlertThread thread = new AlertThread();
//                thread.start();
//
//                break;


        }
    }


    public class ReceiveThread extends Thread {

        public void run() {
            try {
                Socket socket;
                DataOutputStream output;

                socket = new Socket(ip, port);

                output = new DataOutputStream(socket.getOutputStream());

                output.writeUTF("4");
                output.flush();

                BufferedReader reader = new BufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));

                while (true) {

                    read = reader.readLine();

                    if (read.equals("end")) {
                        break;
                    }
                    split1(read);

                    second.add(second1);
                    third.add(third1);
                    fourth.add(fourth1);
                }

//                for (int i = 0; i < second.size(); i++) {
////                    if(third.get(i) != null) {
//                        lati = Double.valueOf(third.get(i)).doubleValue();
//                        longti = Double.valueOf(fourth.get(i)).doubleValue();
//                        addMarker(second.get(i), lati, longti);
////                    }
//                }


//                second.clear();
//                third.clear();
//                fourth.clear();

                socket.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void split1(String buffer) {

        second1 = buffer.split("/")[0];
        third1 = buffer.split("/")[1];
        fourth1 = buffer.split("/")[2];

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

