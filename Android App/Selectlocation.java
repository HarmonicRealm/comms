package com.example.a3010_project_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Selectlocation extends Activity {

    //Define the textView and all buttons
    Button Seeallvalues;
    Button Viewlocations;
    static Button plot1;
    static Button plot2;
    static Button plot3;
    public static TextView locations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlocation);


        Seeallvalues = findViewById(R.id.seeallvalues);
        Viewlocations = findViewById(R.id.Viewlocations);
        plot1 = findViewById(R.id.plot1);
        plot2 = findViewById(R.id.plot2);
        plot3 = findViewById(R.id.plot3);
        locations = findViewById(R.id.locations);


        FetchLocations process = new FetchLocations();
        process.execute();


        //Set a click listener so that when the Seeallvalues is pressed,
        //the toViewallvalues function is called.
        Seeallvalues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toViewallvalues(v);
            }
        });

        Viewlocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FetchLocations process = new FetchLocations();
                process.execute();
            }
        });



        plot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toViewplots(v);

            }
        });

        plot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toViewplots(v);
            }
        });

        plot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toViewplots(v);
            }
        });


    }

    //The toViewallvalues function leads the user to the Viewallvalues activity.
    public  void toViewallvalues(View v){
        Intent Viewallvalues = new Intent(this,Viewallvalues.class);
        startActivity(Viewallvalues);

    }

    public void toViewplots(View v){
        Intent plots = new Intent(this, Plots.class);
        startActivity(plots);
    }


}
