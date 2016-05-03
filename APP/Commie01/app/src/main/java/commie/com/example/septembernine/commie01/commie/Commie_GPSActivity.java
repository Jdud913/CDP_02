package commie.com.example.septembernine.commie01.commie;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import commie.com.example.septembernine.commie01.R;

/**
 * Created by septembernine on 2016. 5. 3..
 */
public class Commie_GPSActivity extends Activity {


    private Button btnShowLocation;
    private TextView txtLat;
    private TextView txtLon;

    // GPSTracker class
    private GpsInfo gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commie_gpxactivity);

        btnShowLocation = (Button) findViewById(R.id.btn_start);
        txtLat = (TextView) findViewById(R.id.Latitude);
        txtLon = (TextView) findViewById(R.id.Longitude);

        // GPS
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                gps = new GpsInfo(Commie_GPSActivity.this);
                // GPS
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    txtLat.setText(String.valueOf(latitude));
                    txtLon.setText(String.valueOf(longitude));

                    String msg = "Latitude : " + latitude + "\nlongitude: " + longitude;
                    Toast.makeText(
                            getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    // GPS
                    gps.showSettingsAlert();
                }
            }
        });
    }

}