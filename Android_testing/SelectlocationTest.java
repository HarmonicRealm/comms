package com.example.a3010_project_attempt4;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xml.sax.Locator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class SelectlocationTest {

    @Rule
    public ActivityTestRule<Selectlocation> mySelectRule = new ActivityTestRule<>(Selectlocation.class, true,false);
    private Selectlocation myActivity = null;

    Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(ViewCorrvalues.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(Viewallvalues.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        myActivity = mySelectRule.getActivity();
    }

    @Test
    public void testLaunch_Selectlocation(){
        //check if the top title "Select Your Location" is set up.
        Espresso.onView(withId(R.id.textView2)).check((ViewAssertion) withText("Select Your Location"));

        //check if the two buttons in the home page is set up.
        Espresso.onView(withId(R.id.button3)).check((ViewAssertion) withText("VIEW ALL VALUES"));
        //check is there the locations textView to fetch data.
        Espresso.onView(withId(R.id.locations));
    }

    @Test
    //test the viewallvalues button at the button
    public void testViewallvalues_button(){
        //perform "view all values" button click.
        assertNotNull(myActivity.findViewById(R.id.viewallvalues));
        onView(withId(R.id.viewallvalues)).perform(click());

        //check if the function "viewallvalues()" is called.
        Activity viewallvalues = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        assertNotNull(viewallvalues);
    }

    @Test
    //Test if the textView "locations" shows the correct location table.
    public void testLocation_Table(){

        Espresso.onView(withId(R.id.locations)).check((ViewAssertion) withText(fetchLocation.location_table));

    }

    public void testHyperlink(){

        //Click the location "mooney's bay".
        Espresso.onView(withText("Mooney's bay")).perform(click());

        //Test if if the corresponding values shows up.
        Activity veiwcorrvalues = getInstrumentation().waitForMonitorWithTimeout(monitor1,5000);
        assertNotNull(veiwcorrvalues);


    }



    @After
    public void tearDown() throws Exception {
        myActivity = null;
    }




}