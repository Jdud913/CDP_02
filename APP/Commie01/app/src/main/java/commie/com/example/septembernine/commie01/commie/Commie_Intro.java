package commie.com.example.septembernine.commie01.commie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import commie.com.example.septembernine.commie01.R;


public class Commie_Intro extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commie__intro);

        ImageView image01 = (ImageView) findViewById(R.id.imageView01);

        Animation anim00 = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        image01.startAnimation(anim00);

        Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit); //activity 전환 시 anim
                Intent i = new Intent(Commie_Intro.this, Commie_Bluetooth.class);
                startActivity(i);
                finish();
            }
        }, 3000); //3초후 자동 화면 전환
    }


}