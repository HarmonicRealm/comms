package com.example.a3010_project_androidapp;

import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;

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

public class PhTimePlot extends AsyncTask<Void,Void,Void> {
    String values = "";
    String locations = "";
    String location_name = FetchLocations.linktext;

    int count = 0;
    int count2 = 0;
    @Override
    protected Void doInBackground(Void... voids) {


        try {
            URL url2 = new URL("https://api.myjson.com/bins/rea4e");//values
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
            InputStream in2 = new BufferedInputStream(httpURLConnection2.getInputStream());
            values = readStream(in2);
            JSONArray JA2 = new JSONArray(values);

            for(int z = 0 ;z < JA2.length();z++){
                JSONObject JO2 = (JSONObject) JA2.get(z);
                String this_location = JO2.get("location").toString();

                if(this_location.equals(location_name)){
                    count = count + 1;
                }
            }

            DataPoint[] DataPoint_PH = new DataPoint[count];

            for(int i = 0; i < JA2.length() ; i++){
                JSONObject JO2 = (JSONObject) JA2.get(i);
                String this_location = JO2.get("location").toString();

                if(this_location.equals(location_name)){
                    count2 = count2 + 1;

                    int Turbidity = JO2.getInt("Turbidity");

                    String date = JO2.get("Date").toString();
                    date = date.replace("-", "");

                    int intdate = Integer.parseInt(date);

                    DataPoint_PH[count] = new DataPoint(intdate,Turbidity);


                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
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
}


