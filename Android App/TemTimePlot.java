package com.example.a3010_project_androidapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TemTimePlot extends AsyncTask<Void, Void, Void> {
    String values = "";
    String locations = "";
    String location_name = FetchLocations.linktext;

    int orange = R.color.orange;
    int purple = R.color.purple;
    int red = R.color.red;
    int green = R.color.green;


    int count = -1;
    int count2 = 0;

    @SuppressLint("WrongThread")

    protected Void doInBackground(Void... avoid) {


        try {
            //Set the connection for locations
            URL url1 = new URL("https://api.myjson.com/bins/a4pku");//locations
            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
            InputStream in1 = new BufferedInputStream(httpURLConnection1.getInputStream());
            locations = readStream(in1);
            JSONArray JA1 = new JSONArray(locations);

            //Set the connection for values
            URL url2 = new URL("https://api.myjson.com/bins/rea4e");//values
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
            InputStream in2 = new BufferedInputStream(httpURLConnection2.getInputStream());
            values = readStream(in2);
            JSONArray JA2 = new JSONArray(values);


            for(int z = 0 ;z < JA2.length();z++){
                JSONObject JO2 = (JSONObject) JA2.get(z);
                String this_location = JO2.get("location").toString();

                if(this_location.equals(location_name)){
                    count2 = count2 + 1;
                }
            }




            DataPoint[] DataPoint00 = new DataPoint[count2];
            DataPoint[] DataPoint25 = new DataPoint[count2];
            DataPoint[] DataPoint50 = new DataPoint[count2];
            DataPoint[] DataPoint75 = new DataPoint[count2];


            for(int i = 0 ; i < JA2.length(); i++){
                JSONObject JO2 = (JSONObject) JA2.get(i);
                String this_location = JO2.get("location").toString();


                if(this_location.equals(location_name)){
                    count = count + 1;
                    int Tem00 = JO2.getInt("0.00m");
                    int Tem25 = JO2.getInt("0.25m");
                    int Tem50 = JO2.getInt("0.50m");
                    int Tem75 = JO2.getInt("0.75m");

                    String date = JO2.get("Date").toString();
                    date = date.replace("-", "");

                    int intdate = Integer.parseInt(date);

                    DataPoint00[count] = new DataPoint(intdate,Tem00);
                    DataPoint25[count] = new DataPoint(intdate,Tem25);
                    DataPoint50[count] = new DataPoint(intdate,Tem50);
                    DataPoint75[count] = new DataPoint(intdate,Tem75);

                }
            }

            //Creates four bunch of points for different lines representing different depth in the plot;
            /*LineGraphSeries<DataPoint> series00 = new LineGraphSeries<>(DataPoint00);
            LineGraphSeries<DataPoint> series25 = new LineGraphSeries<>(DataPoint25);
            LineGraphSeries<DataPoint> series50 = new LineGraphSeries<>(DataPoint50);
            LineGraphSeries<DataPoint> series75 = new LineGraphSeries<>(DataPoint75);*/

            LineGraphSeries<DataPoint> series00 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(191127,21),
                    new DataPoint(191128,20),
                    new DataPoint(191129,21),
                    new DataPoint(191130,20)
            });
            LineGraphSeries<DataPoint> series25 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(191127,18),
                    new DataPoint(191128,19),
                    new DataPoint(191129,19),
                    new DataPoint(191130,19)

            });
            LineGraphSeries<DataPoint> series50 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(191127,16),
                    new DataPoint(191128,16),
                    new DataPoint(191129,16),
                    new DataPoint(191130,16)
            });
            LineGraphSeries<DataPoint> series75 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(191127,15),
                    new DataPoint(191128,14),
                    new DataPoint(191129,14),
                    new DataPoint(191130,15)
            });



            series00.setAnimated(true);
            series25.setAnimated(true);
            series50.setAnimated(true);
            series75.setAnimated(true);

            series00.setDrawDataPoints(true);
            series25.setDrawDataPoints(true);
            series50.setDrawDataPoints(true);
            series75.setDrawDataPoints(true);

            series00.setColor(R.color.orange);
            series25.setColor(R.color.purple);
            series50.setColor(R.color.red);
            series75.setColor(R.color.green);

            series00.setTitle("0.00m");
            series25.setTitle("0.25m");
            series50.setTitle("0.50m");
            series75.setTitle("0.75m");

            Plots.plots.addSeries(series00);
            Plots.plots.addSeries(series25);
            Plots.plots.addSeries(series50);
            Plots.plots.addSeries(series75);

            Plots.plots.setTitle("Temperature in Ceslius at 0, 25cm, 50cm, and 75m over Time");
            Plots.plots.getGridLabelRenderer().setVerticalAxisTitle("Temperature (C)");
            Plots.plots.getGridLabelRenderer().setHorizontalAxisTitle("Time/Date");
            Plots.plots.getLegendRenderer().setVisible(true);

            Plots.plots.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }




        return null;
    }

    protected String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }



    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);


    }
}
