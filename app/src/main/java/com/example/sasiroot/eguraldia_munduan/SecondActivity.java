package com.example.sasiroot.eguraldia_munduan;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by sasiroot on 3/9/18.
 */

public class SecondActivity extends Activity {

    private static final String API ="https://api.darksky.net/forecast/554fc93724742248c61a8c8c1f4ad946/%s"+"?units=si&lang=es";

    Typeface weatherFont;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    ImageView weatherIcon;
    TextView currentTemperatureField;
    int backgroundImageId = 0;

    Button new_york;
    Button paris;
    Button london;
    Button los_angeles;
    Button tokyo;

    String coord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("CityName");
        setupNavBtn();



        chooseCity(cityName);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rLayout);
        rl.setBackgroundResource(backgroundImageId);

        int alphaAmount = 70;
        rl.getBackground().setAlpha(alphaAmount);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        cityField = (TextView)findViewById(R.id.city_field);
        updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);
        cityField.setText(cityName);



        final JSONObject json = getJSON(coord);
        renderWeather(json);



        eguraldiaDbOpenHelper eguraldiaDbOpenHelper= new eguraldiaDbOpenHelper(getApplicationContext(), "Eguraldia",null,1);
        SQLiteDatabase eguraldiaDB =
                eguraldiaDbOpenHelper.getReadableDatabase();

        // Egiaztatu datu-basea ondo ireki den
        if (eguraldiaDB == null) {
            // Sortu eta erauktsi jakinarazpen bat akats bat gertatu dela           erakusteko
            Toast.makeText(SecondActivity.this, "baaaaaaad", Toast.LENGTH_LONG).show();
            // Bukatu "onClick" metodoa

            return;

        }
        ContentValues erregistroa = new ContentValues();
        erregistroa.put("hiria",
                cityField.getText().toString());
        erregistroa.put("uneko_tenperatura",
                currentTemperatureField.getText().toString());
        erregistroa.put("komentarioa",
                updatedField.getText().toString());
        erregistroa.put("predikzioa",
                detailsField.getText().toString());

        // Exekutatu datu-basean datuak txertatzeko agindua
        eguraldiaDB.insert("Eguraldia", null, erregistroa);
        // Itxi datu-basea
        eguraldiaDbOpenHelper.close();
        // Sortu eta erakutsi jakinarazpen bat kontaktua ondo gehitu dela erakusteko
        //Toast.makeText(SecondActivity.this, "goooooood insert", Toast.LENGTH_LONG).show();


    }


    private void chooseCity(String cityName){
        switch (cityName){
            case("New York"):
                coord = "40.730610,-73.935242";
                backgroundImageId = R.drawable.nuevaayork;
                break;
            case("London"):
                coord = "51.5072,-0.1275";
                backgroundImageId = R.drawable.london;
                break;
            case("Los Angeles"):
                coord = "34.0500,-118.2500";
                backgroundImageId = R.drawable.losangeles;
                break;
            case("Paris"):
                coord = "48.8567,2.3508";
                backgroundImageId = R.drawable.paris;
                break;
            case("Tokyo"):
                backgroundImageId = R.drawable.tokyo;
                coord = "35.6833,139.6833";
                break;
            case("Arrankudiaga"):
                coord = "43.256963,-2.923441";
                backgroundImageId = R.drawable.arrankudiaga;
                break;
            case("Ziortza-Bolibar"):
                coord = "43.2497137,-2.5497100999999702";
                backgroundImageId = R.drawable.ziortza;
                break;
            case("Matxinbenta"):
                coord = "43.0778,-2.2278";
                backgroundImageId = R.drawable.matxibenta;
                break;
            case("Axpe"):
                coord = "43.1160000,-2.5985700";
                backgroundImageId = R.drawable.axpe;
                break;
            case("Baliarrain"):
                coord = "43.0729042,-2.1293729";
                backgroundImageId = R.drawable.baliarrain;
                break;
            default:
                coord = "42.3482,-75.1890";
                break;
        }
    }

    private void setupNavBtn(){
        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "second click", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void renderWeather(JSONObject json){
        try {

            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_WEEK);

            String[] days = {"DOM", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB"};
            JSONArray data_array = json.getJSONObject("daily").getJSONArray("data");
            for (int i=0; i<7;i++){
                JSONObject item = data_array.getJSONObject(i);


                String temperatureMax = item.getString("temperatureMax");
                String temperatureMin = item.getString("temperatureMin");
                String icon = item.getString("icon");
                String w_summary = item.getString("summary");
                temperatureMax = temperatureMax.substring(0,2);
                temperatureMin = temperatureMin.substring(0,2);


                detailsField.setText(detailsField.getText()  + days[(today+i)%7] + ": "+temperatureMin+"C - "+temperatureMax +"C "+w_summary+ "\n");
                detailsField.getText();
            }

            String ikono = json.getJSONObject("currently").getString("icon");
            setIrudi(ikono);




            //cityField.setText("New York");
            if(json.getString("timezone").contains("York"))
                cityField.setText("New York");
            if(json.getString("timezone").contains("London"))
                cityField.setText("London");
            if(json.getString("timezone").contains("Los"))
                cityField.setText("Los Angeles");
            if(json.getString("timezone").contains("Paris"))
                cityField.setText("Paris");
            if(json.getString("timezone").contains("Tokyo"))
                cityField.setText("Tokyo");


            currentTemperatureField.setText(json.getJSONObject("currently").getString("temperature") + " \u00b0 C");
            updatedField.setText(

                    json.getJSONObject("currently").getString("summary")
            );



        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static JSONObject getJSON( String coord){
        try {
            URL url = new URL(String.format((API), coord));

            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
            connection.getInputStream();

            System.out.print("CONNECTION:::" + connection.getInputStream());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            System.out.print("url:::");
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            return data;
        }catch(Exception e){
            e.printStackTrace();

            return null;
        }
    }

    public void setIrudi(String icon) {
        try {
            Drawable r;
            switch (icon){
                case "rain":
                r = getResources().getDrawable(R.drawable.jarreo);
                weatherIcon.setImageDrawable(r);
                break;
            case "clear-day":
                r = getResources().getDrawable(R.drawable.sol);
                weatherIcon.setImageDrawable(r);
                break;
            case "clear-night":
                r = getResources().getDrawable(R.drawable.luna);
                weatherIcon.setImageDrawable(r);
                break;
            case "snow":
                r = getResources().getDrawable(R.drawable.nieve);
                weatherIcon.setImageDrawable(r);
                break;
            case "sleet":
                r = getResources().getDrawable(R.drawable.nieve);
                weatherIcon.setImageDrawable(r);
                break;
            case "wind":
                r = getResources().getDrawable(R.drawable.viento);
                weatherIcon.setImageDrawable(r);
                break;
            case "fog":
                r = getResources().getDrawable(R.drawable.niebla);
                weatherIcon.setImageDrawable(r);
                break;
            case "cloudy":
                r = getResources().getDrawable(R.drawable.nublado);
                weatherIcon.setImageDrawable(r);
                break;
            case "partly-cloudy-day":
                r = getResources().getDrawable(R.drawable.nubesyclaros);
                weatherIcon.setImageDrawable(r);
                break;
            case "partly-cloudy-night":
                r = getResources().getDrawable(R.drawable.nublado);
                weatherIcon.setImageDrawable(r);
                break;
            default:
                r=getResources().getDrawable(R.drawable.luna);
                    break;
        }


} catch (Exception e) {
        e.printStackTrace();
        }}




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
