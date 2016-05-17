package com.example.hoyoung.fairy_commie_admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by HoYoung on 2016-05-13.
 */
public class AddBeacon extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbeacon);

        Button bt_OK = (Button)findViewById(R.id.OK_btn);
        Button bt_Return = (Button)findViewById(R.id.Return_btn);

        bt_OK.setOnClickListener(this);
        bt_Return.setOnClickListener(this);


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.OK_btn :
                Toast toast = Toast.makeText(this, "OK", Toast.LENGTH_SHORT);
                toast.show();
                break;

            case R.id.Return_btn :
                Intent intent = new Intent(AddBeacon.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
