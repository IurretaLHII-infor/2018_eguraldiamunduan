package com.example.sasiroot.eguraldia_munduan;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class proba_db extends Activity {

    private static final String API ="https://api.darksky.net/forecast/554fc93724742248c61a8c8c1f4ad946/%s"+"?units=si&lang=es";

    Typeface weatherFont;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
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

        setContentView(R.layout.activity_proba_db);
        Bundle extras = getIntent().getExtras();

        System.out.print("HERRIA" + extras.get("CityName"));
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("CityName");
        setupNavBtn();



        chooseCity(cityName);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rLayout);
        rl.setBackgroundResource(backgroundImageId);

        int alphaAmount = 70;
        rl.getBackground().setAlpha(alphaAmount);

        //spacing issue

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //declare at the top if possible
        cityField = (TextView)findViewById(R.id.city_field);
        updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)findViewById(R.id.weather_icon);
        //weatherFont = Typeface.createFromAsset(this.getAssets(), "fonts/weather.ttf");
        weatherIcon.setTypeface(weatherFont);
        weatherIcon.setText(getString(R.string.weather_clear_night));
        cityField.setText(cityName);

        //Context myContext = this;
        chooseCity(cityName);

        eguraldiaDbOpenHelper eguraldiaDbOpenHelper= new
                eguraldiaDbOpenHelper(getApplicationContext(), "Eguraldia", null, 1);
        // Jarri datu-basea irakurtzeko eran
        SQLiteDatabase EguraldiaDB =
                eguraldiaDbOpenHelper.getReadableDatabase();
        // Egiaztatu datu-basea ondo ireki den
        if (EguraldiaDB == null) {
            // Sortu eta erauktsi jakinarazpen bat akats bat gertatu dela

            Toast.makeText(proba_db.this, "baaaaaaad", Toast.LENGTH_LONG).show();

            // Bukatu "onClick" metodoa
            return;
        }

        // Zehaztu datu-basean kontsultatu nahi diren eremuak edo zutabeak
        String[] eremuak = new String[]{"hiria", "uneko_tenperatura",
                "komentarioa", "predikzioa"};
        // Exekutatu datu-basean datuak kontsultatzeko agindua eta gorde Cursor

        // objektu batean
        Cursor eguraldiaCursor = EguraldiaDB.query("Eguraldia", eremuak, "hiria",
                null, "uneko_tenperatura", "komentarioa", "predikzioa");
        // Sortu eta erakutsi jakinarazpen bat berreskuratutako erregistrokopurua

        Toast.makeText(proba_db.this, "cursoooor", Toast.LENGTH_LONG).show();


        // Ibili "kontaktuakCursor" datu guztiak berreskuratzeko eta

        if (eguraldiaCursor.moveToFirst()) {
            // Kontaktuak zenbatzeko
            int kont = 1;
            do {
                // Gehitu kontaktua "bistaratuEditText" testu-laukian
                cityField.append(
                        eguraldiaCursor.getString(0)
                );
                currentTemperatureField.append(
                        eguraldiaCursor.getString(1)
                );
                updatedField.append(
                        eguraldiaCursor.getString(2)
                );
                detailsField.append(
                        eguraldiaCursor.getString(3)
                );


        }while (eguraldiaCursor.moveToNext());
        eguraldiaCursor.close();            // Itxi datu-basea
        eguraldiaDbOpenHelper.close();
    }


//        final JSONObject json = getJSON(coord);
//        //Log.v("JSON:", json.toString());
//        renderWeather(json);
    }


    //case barik, datu base batean sartu.
    private void chooseCity(String cityName){
        switch (cityName){
            case("New York"):
                coord = "43.256963,-2.923441";
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
                Toast.makeText(proba_db.this, "second click", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void renderWeather(JSONObject json){
        try {
            //detailsField.setText("");
            //cityField.setText("");
            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_WEEK);

            String[] days = {"DOM", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB"};
            JSONArray data_array = json.getJSONObject("daily").getJSONArray("data");
            for (int i=0; i<7;i++){
                JSONObject item = data_array.getJSONObject(i);

                String temperatureMax = item.getString("temperatureMax");
                String temperatureMin = item.getString("temperatureMin");
                String w_summary = item.getString("summary");
                temperatureMax = temperatureMax.substring(0,2);
                temperatureMin = temperatureMin.substring(0,2);

                detailsField.setText(detailsField.getText()  + days[(today+i)%7] + ": "+temperatureMin+" - "+temperatureMax +" "+w_summary+ "\n");
                detailsField.getText();
            }



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

                    // "SUMMARY OF WEEK  : " +
                    json.getJSONObject("daily").getString("summary")
                    // +      "\nTIME ZONE  : " + json.getString("timezone")
            );



        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }


    public static JSONObject getJSON(/*Context context,*/ String coord){
        try {
            // coord = "40.7127,-74.0059";//debug
            URL url = new URL(String.format((API), coord));

            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
