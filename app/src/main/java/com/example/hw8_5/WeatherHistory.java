package com.example.hw8_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class WeatherHistory extends AppCompatActivity {
    ArrayAdapter adapter;
    ListView listView;
    ArrayList<HashMap<String,String>> list;
    JSONObject obj;
    SimpleAdapter sa;
    long unix1;
    long unix2;
    long unix3;
    long unix4;
    long unix5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_history);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = (ListView) findViewById(R.id.simpleListView);
        list = new ArrayList<>();
        Intent intent = getIntent();
        String lat = intent.getStringExtra(DisplayWeather.CUR_LAT);
        String lon = intent.getStringExtra(DisplayWeather.CUR_LON);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            unix1 = Instant.now().getEpochSecond() - 86400;
        }
        else {
            unix1 = System.currentTimeMillis() / 1000L;
            unix1 -= 86400;
        }

        String url1 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat
                + "&lon=" + lon
                + "&dt=" + unix1
                + "&units=imperial&appid=3089cbf15412b1205e432e4d86a8789d";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            JSONObject weather = obj.getJSONObject("current");
                            Double temp = weather.getDouble("temp");
                            Date date = new Date(unix1*1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                            String fdate = sdf.format(date);

                            HashMap<String, String> item1 = new HashMap<>();
                            item1.put("line1", fdate);
                            item1.put("line2", temp.toString() + "F");
                            list.add(item1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Unable to retrieve weather data",
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest1);

        unix2 = unix1 - 86400;
        String url2 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat
                + "&lon=" + lon
                + "&dt=" + unix2
                + "&units=imperial&appid=3089cbf15412b1205e432e4d86a8789d";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            JSONObject weather = obj.getJSONObject("current");
                            Double temp = weather.getDouble("temp");
                            Date date = new Date(unix2*1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                            String fdate = sdf.format(date);

                            HashMap<String, String> item2 = new HashMap<>();
                            item2.put("line1", fdate);
                            item2.put("line2", temp.toString() + "F");
                            list.add(item2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Unable to retrieve weather data",
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest2);

        unix3 = unix2 - 86400;
        String url3 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat
                + "&lon=" + lon
                + "&dt=" + unix3
                + "&units=imperial&appid=3089cbf15412b1205e432e4d86a8789d";
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            JSONObject weather = obj.getJSONObject("current");
                            Double temp = weather.getDouble("temp");
                            Date date = new Date(unix3*1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                            String fdate = sdf.format(date);

                            HashMap<String, String> item3 = new HashMap<>();
                            item3.put("line1", fdate);
                            item3.put("line2", temp.toString() + "F");
                            list.add(item3);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Unable to retrieve weather data",
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest3);

        unix4 = unix3 - 86400;
        String url4 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat
                + "&lon=" + lon
                + "&dt=" + unix4
                + "&units=imperial&appid=3089cbf15412b1205e432e4d86a8789d";
        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, url4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            JSONObject weather = obj.getJSONObject("current");
                            Double temp = weather.getDouble("temp");
                            Date date = new Date(unix4*1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                            String fdate = sdf.format(date);

                            HashMap<String, String> item4 = new HashMap<>();
                            item4.put("line1", fdate);
                            item4.put("line2", temp.toString() + "F");
                            list.add(item4);
                            sa = new SimpleAdapter(WeatherHistory.this, list, R.layout.list_template,
                                    new String[] {"line1", "line2"},
                                    new int[] {R.id.listelemdate, R.id.listelemtemp});
                            listView.setAdapter(sa);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Unable to retrieve weather data",
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest4);

        unix5 = unix4 - 86400;
        String url5 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat
                + "&lon=" + lon
                + "&dt=" + unix5
                + "&units=imperial&appid=3089cbf15412b1205e432e4d86a8789d";
        StringRequest stringRequest5 = new StringRequest(Request.Method.GET, url5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            JSONObject weather = obj.getJSONObject("current");
                            Double temp = weather.getDouble("temp");
                            Date date = new Date(unix5*1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                            String fdate = sdf.format(date);

                            HashMap<String, String> item5 = new HashMap<>();
                            item5.put("line1", fdate);
                            item5.put("line2", temp.toString() + "F");
                            list.add(item5);
                            System.out.println(list);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Unable to retrieve weather data",
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest4);
    }
}