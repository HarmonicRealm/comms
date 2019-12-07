package com.example.a3010_project_androidapp;

import android.net.Uri;
import android.os.AsyncTask;

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

public class FetchData extends AsyncTask<Void,Void,Void> {
    String values = "";
    String locations = "";
    String location_id = "";
    String location_name = "";
    String singleObject = "";
    String totalObject = "";

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            //Set the connection for values
            URL url1 = new URL("https://api.myjson.com/bins/10ei9w");//values
            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
            InputStream in1 = new BufferedInputStream((httpURLConnection1.getInputStream()));
            values = readStream(in1);
            JSONArray JA1 = new JSONArray(values);


            //Set the connection for locations
            URL url2 = new URL("https://api.myjson.com/bins/a4pku");//locations
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
            InputStream in2 = new BufferedInputStream((httpURLConnection2.getInputStream()));
            locations = readStream(in2);
            JSONArray JA2 = new JSONArray(locations);

            //This for loop goes through values table.
            for(int i = 0; i < JA1.length(); i++){
                JSONObject JO1 = (JSONObject) JA1.get(i);
                location_id = JO1.get("location_id").toString();

                //This for loop goes through locations table.
                for(int z = 0; z < JA2.length(); z++){
                    JSONObject JO2 = (JSONObject) JA2.get(z);
                    location_name = JO2.get("location").toString();

                    if(location_id.equals(JO2.get("location_id").toString())){
                        location_name = JO2.get("location").toString();
                        singleObject =
                                        "Location                        " +
                                                "" + location_name + "\n" +
                                        "Date                               " + JO1.get("tdate").toString() + "\n" +
                                        "Time                              " + JO1.get("ttime").toString() + "\n" +
                                        "PH                                        " + JO1.get("tph").toString() + "\n" +
                                        "Turbidity                             " + JO1.get("tturbidity").toString() + "\n" +
                                        "Temperature at surface   "  + JO1.get("0.00m").toString() +  "\n" +
                                        "Temperature at 0.25m     " + JO1.get("0.25m").toString() + "\n"  +
                                        "Temperature at 0.50m     " + JO1.get("0.50m").toString() + "\n"  +
                                        "Temperature at 0.75m     " + JO1.get("0.75m").toString() + "\n\n\n";

                        totalObject = totalObject + singleObject;
                    }
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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //print the variable totalObject in the allvalues TextView in the Viewallvalues page.
        Viewallvalues.allvalues.setText(this.totalObject);
    }

    //The readStream function helps to read the whole bunch of stream and make it a String.
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
