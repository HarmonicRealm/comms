package com.example.a3010_project_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;

public class Plots extends AppCompatActivity {

    public static GraphView plots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots);

        plots = findViewById(R.id.Viewplots);

        Selectlocation.plot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TemTimePlot process = new TemTimePlot();
                process.execute();
            }
        });

        Selectlocation.plot2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TurTimePlot process = new TurTimePlot();
                process.execute();
            }
        });

        Selectlocation.plot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TemTimePlot process = new TemTimePlot();
        process.execute();


    }
}
