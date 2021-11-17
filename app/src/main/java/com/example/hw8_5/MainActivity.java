package com.example.hw8_5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myweatherapp.EXTRA_MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myweatherapp.EXTRA_MESSAGE2";
    EditText locinput;
    Button submit;
    ImageButton imgbtn;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locinput = (EditText) findViewById(R.id.locinput);
        submit = (Button) findViewById(R.id.submitloc);
        imgbtn = (ImageButton) findViewById(R.id.getloc);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, mPermission)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{mPermission},
                                REQUEST_CODE_PERMISSION);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    Double latitude = gps.getLatitude();
                    Double longitude = gps.getLongitude();
                    Intent intent = new Intent(MainActivity.this, DisplayWeather.class);
                    intent.putExtra(EXTRA_MESSAGE, latitude.toString());
                    intent.putExtra(EXTRA_MESSAGE2, longitude.toString());
                    startActivity(intent);

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Location received", Toast.LENGTH_LONG).show();
                }else{
                    gps.showSettingsAlert();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather();
            }
        });
    }

    public void getWeather() {
        Intent intent = new Intent(this, DisplayWeather.class);
        String search = locinput.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, search);
        startActivity(intent);
    }

}