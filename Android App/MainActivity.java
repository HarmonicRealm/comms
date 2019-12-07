package com.example.a3010_project_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Define the two buttons on the home page
    Button Selectlocation;
    Button Viewallvalues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Selectlocation = findViewById(R.id.selectlocation);
        Viewallvalues = findViewById(R.id.Viewallvalues);

        //Set the button listener, so when the Selectlocation button is clicked,
        //selectlocation() function is called
        Selectlocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectlocation(v);
            }
        });

        //Set the button listener, so when the Viewvalues button is clicked,
        //selectlocation() function is called
        Viewallvalues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewallvalues(v);
            }
        });
    }



    //selectlocation function leads the user to Selectlocation activity.
    public void selectlocation(View v){
        Intent Selectlocation = new Intent (this, Selectlocation.class);
        startActivity(Selectlocation);
    }

    //viewallvalues functio  leads the user to Viewallvalues activity.
    public void viewallvalues(View v){
        Intent Viewallvalues = new Intent(this,Viewallvalues.class);
        startActivity(Viewallvalues);
    }

}
