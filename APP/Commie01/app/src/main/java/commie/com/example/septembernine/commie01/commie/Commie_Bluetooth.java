package commie.com.example.septembernine.commie01.commie;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import commie.com.example.septembernine.commie01.R;
import commie.com.example.septembernine.commie01.reco.*;
/**
 * Created by septembernine on 2016. 4. 1..
 */
public class Commie_Bluetooth extends Activity {
    //Debugging
    private static final String TAG = "BluetoothService";

    //bluetooth를 on/off 하게 할 수 있는 BluetoothAdapter
    public static BluetoothAdapter btAdapter;

    //bluetooth가 허용된 상태라면 다음 activity로 넘어가기위한 조건
    private int checkNum = 0;

    ImageView image03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_commie__bluetooth);

        //BluetoothAdapter 얻기
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (getDeviceState()) {
            Toast toast = Toast.makeText(Commie_Bluetooth.this, "블루투스가 지원됩니다", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            //test용 toast
            Toast toast = Toast.makeText(Commie_Bluetooth.this, "블루투스가 지원되지 않아 앱이 종료됩니다", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }

        //BLE기능이 적용되는 기기인지 확인 - BLE기능을 지원한다는 말은 빼는게 좋은 듯
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE기능을 지원하지 않아 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
            finish();
        }

        ImageViewTouch();
    }


    public void doNextActivity(){

        Intent intent = new Intent(this, RecoBackgroundRangingService.class);
        startService(intent);

        Intent i = new Intent(Commie_Bluetooth.this, Commie_Beacon.class);
        startActivity(i);
        finish();

    }


    //Bluetooth가 사용가능한 기기인지 판단
    public boolean getDeviceState(){
        Log.i(TAG, "Check the Bluetooth support");

        if(btAdapter == null){
            return false;
        } else{
            Log.d(TAG, "Bluetooth is available");
            return true;
        }
    }

    //bluetooth허용시 터치하는 메소드
    public void ImageViewTouch(){

        image03= (ImageView)findViewById(R.id.imageView03);

        image03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBluetooth();

            }
        });


        //20초 동안 아무런 touch없으면 앱 종료!
        Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(checkNum != 1) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }, 20000);


    }

    public void enableBluetooth(){

        Log.i(TAG, "Check the enabled Bluetooth");

        //.isEnable()을 통하여 현재 bluetooth가 on인지 확인
        if(btAdapter.isEnabled()){

            Log.d(TAG, "Bluetooth Enable Now");

            checkNum = 1;
            doNextActivity();

        } else{

            btAdapter.enable();


            Log.d(TAG, "Bluetooth Enable Request");

            checkNum = 1;
            doNextActivity();
        }


    }



}
