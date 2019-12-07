package com.example.a3010_project_androidapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
import java.net.ProtocolException;
import java.net.URL;

import static androidx.core.content.ContextCompat.startActivity;

public class FetchLocations extends AsyncTask<Void, Void, Void>  {
    String locations = "";
    String values = "";
    String singleLocation = "";
    String singleCorrvalue = "";
    String totalCorrvalues = "";
    int location_length;

    static String linktext = "";
    static String location_id = "";
    SpannableStringBuilder builder = new SpannableStringBuilder();

    /*int orange = R.color.orange;
    int purple = R.color.purple;
    int red = R.color.red;
    int green = R.color.green;

    public static String[] finallist;*/



    @SuppressLint("WrongThread")
    @Override
    protected Void doInBackground(Void... voids) {


        try {
            //Set the connection for locations
            URL url1 = new URL("https://api.myjson.com/bins/a4pku");//locations
            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
            InputStream in1 = new BufferedInputStream(httpURLConnection1.getInputStream());
            locations = readStream(in1);
            JSONArray JA1 = new JSONArray(locations);

            //Set the connection for values
            URL url2 = new URL("https://api.myjson.com/bins/10ei9w");//values
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
            InputStream in2 = new BufferedInputStream(httpURLConnection2.getInputStream());
            values = readStream(in2);
            final JSONArray JA2 = new JSONArray(values);




            for(int i = 0; i < JA1.length(); i++){
                //String linktext = "";
                //String location_id = "";

                JSONObject JO1 = (JSONObject)JA1.get(i);
                linktext = JO1.get("location").toString();
                location_id = JO1.get("location_id").toString();

                singleLocation = "Location    " + linktext + "\n\n";

                SpannableString hyperlink = new SpannableString(singleLocation);

                final String finalLinktext = linktext;
                final String finalLocation_id = location_id;
                //************************
                /*int count = 0;
                for(int j = 0; j < JA2.length();j++){
                    JSONObject JO2 = (JSONObject) JA2.get(j);
                    String this_location = JO2.get("location").toString();
                    if(this_location.equals(finalLinktext)){
                        count = count + 1;
                    }
                }

                finallist = new String[count];*/




                //************************


                ClickableSpan click  = new ClickableSpan() {


                    @Override
                    public void onClick(@NonNull View widget) {
                        //Selectlocation selectActivity = new Selectlocation();
                        //Intent toCorrvalues = new Intent();
                        //toCorrvalues.setClass(selectActivity.getApplicationContext(),Corrvalues.class);
                        //selectActivity.getApplicationContext().startActivity(toCorrvalues);





                        for (int z = 0; z < JA2.length(); z++){


                            try {
                                 JSONObject JO2 = (JSONObject)JA2.get(z);
                                if(finalLocation_id.equals(JO2.get("location_id").toString())){
                                    singleCorrvalue =
                                                    "Location                             " + finalLinktext + "\n" +
                                                    "Date                                    " + JO2.get("tdate").toString() + "\n" +
                                                    "Time                                   " + JO2.get("ttime").toString() + "\n" +
                                                    "PH                                       "   + JO2.get("tph").toString() + "\n" +
                                                    "Turbidity                             " + JO2.get("tturbidity").toString()  +  "\n" +
                                                    "Temperature at surface   " + JO2.get("0.00m").toString() + "\n" +
                                                    "Temperature at 0.25m     " + JO2.get("0.25m").toString() + "\n" +
                                                    "Temperature at 0.50m     " + JO2.get("0.50m").toString() + "\n" +
                                                    "Temperature at 0.75m     " + JO2.get("0.75m").toString() + "\n\n\n";

                                    totalCorrvalues = totalCorrvalues + singleCorrvalue;

                                    //*********************************************


                                    //***********************************************

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        //TemTimePlot temperaturePlot = new TemTimePlot();
                        //temperaturePlot.onPostExecute(finalLinktext);


                        if(totalCorrvalues.equals("")){
                            Selectlocation.locations.setText(" Currently there is no data  " + "\n" +
                                    " mearsured at the chosen location");
                        }
                        else{
                            Selectlocation.locations.setText(totalCorrvalues);
                        }






                    }


                };

                location_length = linktext.length();

                hyperlink.setSpan(click,12,12 + location_length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(hyperlink);


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
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

        Selectlocation.locations.setMovementMethod(LinkMovementMethod.getInstance());
        Selectlocation.locations.setClickable(true);
        Selectlocation.locations.setText(builder);
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

    public static String passString(String str){
        return str;
    }





}
