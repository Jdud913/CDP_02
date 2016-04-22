package com.example.hoyoung.fairy_commie_user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Bluetooth_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        ImageView image01 = (ImageView) findViewById(R.id.imageView02);

        Animation anim00 = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        image01.startAnimation(anim00);


        Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit); //activity 전환 시 anim
                Intent i = new Intent(Bluetooth_Activity.this, Login_Activity.class);
                startActivity(i);
                finish();
            }
        }, 3000); //3초후 자동 화면 전환


    }

}
