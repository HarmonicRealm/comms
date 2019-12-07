package com.example.a3010_project_attempt4;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class ViewallvaluesTest {

    public ActivityTestRule<Viewallvalues> MyAllRule = new ActivityTestRule<>(Viewallvalues.class,true,false);
    private Viewallvalues myActivity = null;



    @Before
    public void setUp() throws Exception {
        myActivity = MyAllRule.getActivity();
    }

    @Test
    public void testLaunch_Viewallvalues(){
        //check if the top title "View All Values" is set up.
        Espresso.onView(withId(R.id.textView3)).check((ViewAssertion) withText("View All Values"));

        //check if the "allvalues" textView is set up.
        Espresso.onView(withId(R.id.allvalues));
    }

    @Test
    //Test if the "all value" table equal to the fetched all value table
    public void testAllvalues_table() {
        Espresso.onView(withId(R.id.allvalues)).check((ViewAssertion)withText(fetchData.finalvalue));
    }

    @After
    public void tearDown() throws Exception {
        myActivity = null;
    }


}