package com.example.bane_.weather_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText cityField;
    TextView lastUpdate;
    ImageView weatherIcon;
    TextView informations;
    TextView temperature;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityField = (EditText) findViewById(R.id.city_field);
        lastUpdate = (TextView) findViewById(R.id.lastUpdate);
        weatherIcon = (ImageView) findViewById(R.id.imageView);
        informations = (TextView) findViewById(R.id.informations);
        temperature = (TextView) findViewById(R.id.temperature);
        check = (Button) findViewById(R.id.checkWeather);

    }

    public void onClick(View view) {
        new GetListOfCountryNames().execute(cityField.getText().toString());
    }

    private class GetListOfCountryNames extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String responseData = "";
            String link = "http://samples.openweathermap.org/data/2.5/weather?q=" +params[0]+ "&appid=b1b15e88fa797225412429c1c50c122a1";

            try {
                // Creating http request
                URL obj = new URL(link);

                //   URL obj = new URL("http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122");

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //add request header
                con.setRequestMethod("GET");


                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                while ((line = br.readLine()) != null)
                    responseData += line;
                con.disconnect();

                int responseCode = con.getResponseCode();

                System.out.println("Response Code : " + responseCode);
                System.out.println("Response Data: " + responseData);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseData;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
               // lastUpdate.setText(result);
                Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/10d.png").resize(200, 200).into(weatherIcon);
                JSONObject response = new JSONObject(result);
                  //rJSONObject weather = response.getJSONObject("weather");
                temperature.setText(response.getString("base"));


                // Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + weather.getString("icon") + "png").into(weatherIcon);
               // Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/10d.png").into(weatherIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
