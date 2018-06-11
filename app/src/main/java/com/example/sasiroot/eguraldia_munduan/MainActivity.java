package com.example.sasiroot.eguraldia_munduan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//add comments
public class MainActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case R.id.new_york:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","New York");
                startActivity(i);

                break;
            case R.id.london:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","London");
                startActivity(i);
                break;
            case R.id.paris:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Paris");
                startActivity(i);
                break;
            case R.id.tokyo:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Tokyo");
                startActivity(i);
                break;
            case R.id.los_angeles:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Los Angeles");
                startActivity(i);
                break;
            case R.id.arrankudiaga:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Arrankudiaga");
                startActivity(i);

                break;
            case R.id.matxinbenta:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Matxinbenta");
                startActivity(i);

                break;
            case R.id.ziortza:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Ziortza-Bolibar");
                startActivity(i);

                break;
            case R.id.axpe:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Axpe");
                startActivity(i);

                break;
            case R.id.baliarrain:
                i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CityName","Baliarrain");
                startActivity(i);

                break;

            default:
                break;
        }

    }


}



