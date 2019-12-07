package com.example.a3010_project_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Viewallvalues extends AppCompatActivity {

    //Defines the allvalues TextView.
    public static TextView allvalues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallvalues);

        allvalues = findViewById(R.id.Allvalues);

        //Implements the FetchData class in the Viewallvalues page, once this page is opened.
        FetchData process = new FetchData();
        process.execute();


    }
}
