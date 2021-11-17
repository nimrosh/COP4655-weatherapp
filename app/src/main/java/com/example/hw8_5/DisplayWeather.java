package com.example.hw8_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class DisplayWeather extends AppCompatActivity {
    public static final String CUR_LAT = "com.example.myweatherapp.LATITUDE";
    public static final String CUR_LON = "com.example.myweatherapp.LONGITUDE";

    TextView test;
    TextView winfo;
    Button mapbtn;
    ImageButton ttsbtn;
    String url;
    JSONObject jsonresp;
    BottomNavigationView bottomnav;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);
    }

    @Override
    protected void onStart() {
        super.onStart();

        test =  (TextView) findViewById(R.id.output);
        winfo = (TextView) findViewById(R.id.weatherinfo);
        ttsbtn = (ImageButton) findViewById(R.id.tsbtn);
        bottomnav = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        ttsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tospeak = test.getText().toString() + " " + winfo.getText().toString();
                tts.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Intent intent = getIntent();

        url = "https://api.openweathermap.org/data/2.5/weather?";
        if (intent.getStringExtra(MainActivity.EXTRA_MESSAGE2) != null) {
            System.out.println("getting coordinates");
            String lon = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
            String lat = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            url += "lat=" + lat;
            url += "&lon=" + lon;
        }
        else {
            String location = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            if (isNumeric(location)) {
                System.out.println("zip detected");
                System.out.println(location);
                url += "zip=" + location;
            }
            else {
                System.out.println("city detected");
                url += "q=" + location;
            }
        }
        url +=  "&units=imperial&appid=3089cbf15412b1205e432e4d86a8789d";
        System.out.println(url);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            jsonresp = new JSONObject(response);
                            JSONObject coord = jsonresp.getJSONObject("coord");
                            JSONArray warray = jsonresp.getJSONArray("weather");
                            JSONObject weather = warray.getJSONObject(0);
                            JSONObject main = jsonresp.getJSONObject("main");
                            JSONObject wind = jsonresp.getJSONObject("wind");
                            System.out.println(weather);
                            System.out.println("hi");
                            String name = jsonresp.getString("name");
                            test.setText("Weather for " + name);

                            String weatherdata = "";
                            String wdesc = weather.getString("description");
                            weatherdata += "Description: " + wdesc + "\n";
                            Double temp = main.getDouble("temp");
                            weatherdata += "Temperature: " + temp + " F\n";
                            Double feels_like = main.getDouble("feels_like");
                            weatherdata += "Feels like: " + feels_like + " F\n";
                            Double high = main.getDouble("temp_max");
                            weatherdata += "High: " + high + " F\n";
                            Double low = main.getDouble("temp_min");
                            weatherdata += "Low: " + low + " F\n";
                            int humidity = main.getInt("humidity");
                            weatherdata += "Humidity: " + humidity + "%\n";
                            int pressure = main.getInt("pressure");
                            weatherdata += "Pressure: " + pressure + " hPa\n";
                            Double speed = wind.getDouble("speed");
                            weatherdata += "Wind speed: " + speed + " mph\n";
                            Double lat = coord.getDouble("lat");
                            weatherdata += "Latitude: " + lat.toString() + "\n";
                            Double lon = coord.getDouble("lon");
                            weatherdata += "Longitude: " + lon.toString() + "\n";

                            //ArrayAdapter arrayAdapter = new ArrayAdapter(DisplayWeather.this, android.R.layout.simple_list_item_1, iteminfo);
                            //list.setAdapter(arrayAdapter);
                            winfo.setText(weatherdata);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                test.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);

        bottomnav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wmap:
                        displayMap();
                        break;
                }
                return true;
            }
        });

    }

    public void displayMap() {
        try {
            JSONObject coord = jsonresp.getJSONObject("coord");
            Intent intent = new Intent(this, GoogleMaps.class);
            Double lat = coord.getDouble("lat");
            Double lon = coord.getDouble("lon");
            intent.putExtra(CUR_LAT, lat.toString());
            intent.putExtra(CUR_LON, lon.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}