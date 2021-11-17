package com.example.hw8_5;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private final int REQ_CODE = 100;
    public static final String EXTRA_MESSAGE = "com.example.myweatherapp.EXTRA_MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myweatherapp.EXTRA_MESSAGE2";
    EditText locinput;
    Button submit;
    ImageButton imgbtn;
    ImageButton spbtn;
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
        spbtn = (ImageButton) findViewById(R.id.speaksearch);

        spbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    locinput.setText(result.get(0).toString());
                }
                break;
            }
        }
    }
}