package com.example.hoyoung.fairy_commie_user;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class IntroActivity extends Activity {

    //Debugging
    private static final String TAG = "BluetoothService";
    final Context context = this;

    //bluetooth를 on/off 하게 할 수 있는 BluetoothAdapter
    public static BluetoothAdapter btAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

         //BluetoothAdapter 얻기
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        //.isEnable()을 통하여 현재 bluetooth가 on인지 확인
        if(btAdapter.isEnabled()){
            Log.d(TAG, "Bluetooth Enable Now");
        } else{

            // 제목셋팅
            alertDialogBuilder.setTitle("블루투스 ON/OFF");

            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage("Bluetooth를 켜시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // 프로그램을 종료한다
                            IntroActivity.this.finish();
                        }
                    })
                    .setNegativeButton("켜기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // 다이얼로그를 취소한다
                            //dialog.cancel();
                            //Intent intent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //startActivity(intent);
                            btAdapter.enable();

                            Handler mhandler = new Handler();
                            mhandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit); //activity 전환 시 anim
                                    Intent i = new Intent(IntroActivity.this, MyLocation.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 3000); //3초후 자동 화면 전환



                        }
                    });

            // 다이얼로그 생성
            AlertDialog alertDialog = alertDialogBuilder.create();

            // 다이얼로그 보여주기
            alertDialog.show();


            //btAdapter.enable();


            Log.d(TAG, "Bluetooth Enable Request");


        }

    }

}
