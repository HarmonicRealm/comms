package com.example.a3010_project_attempt4;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.repeatedlyUntil;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> MyMainRule = new ActivityTestRule<>(MainActivity.class,true,false);
    public ActivityTestRule<Selectlocation> MySelectRule = new ActivityTestRule<>(Selectlocation.class,true,false);
    public ActivityTestRule<Viewallvalues> MyAllRule = new ActivityTestRule<>(Viewallvalues.class,true,false);

    private MainActivity myActivity = null;
    private Selectlocation mySelect = null;
    private Viewallvalues myViewall = null;



    //Define monitors for the classes Selectlocation and Viewallvalues that will be implemented later.
    Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(Selectlocation.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(Viewallvalues.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        myActivity = MyMainRule.getActivity();
        mySelect = MySelectRule.getActivity();
        myViewall = MyAllRule.getActivity();

    }

    @Test
    public void testLaunch_Homepage(){
        //check if the top title "WaterYourWater" is set up.
        Espresso.onView(withId(R.id.textView1));//.check((ViewAssertion) withText("WatchYourWater"));

        //check if the two buttons in the home page is set up.
        Espresso.onView(withId(R.id.selectlocation));//.check((ViewAssertion) withText("SELECT YOUR LOCATION"));
        Espresso.onView(withId(R.id.viewallvalues));//check((ViewAssertion) withText("VIEW ALL VALUES"));
    }

    @Test
    public void testSelectlocation_function() {
        //perform "select your location" button click.
        assertNotNull(myActivity.findViewById(R.id.selectlocation));
        onView(withId(R.id.selectlocation)).perform(click());

        //check the seleclocation class is implemented when the button "select your location" is pressed
        //Activity selectlocation = getInstrumentation().waitForMonitorWithTimeout(monitor1,5000);
        mySelect = (Selectlocation) monitor1.waitForActivityWithTimeout(5000);
        assertNotNull(mySelect);

    }

    @Test
    public void testViewallvalues_function() {
        //perform "view all values" button click.
        assertNotNull(myActivity.findViewById(R.id.viewallvalues));
        Espresso.onView(withId(R.id.viewallvalues)).perform(click());

        //check if the function "viewallvalues()" is called.
        Activity viewallvalues = getInstrumentation().waitForMonitor(monitor2);
        assertNotNull(viewallvalues);


    }

    @After
    public void tearDown() throws Exception {
        myActivity = null;
    }
}