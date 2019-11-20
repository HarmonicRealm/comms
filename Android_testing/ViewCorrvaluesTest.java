package com.example.a3010_project_attempt4;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class ViewCorrvaluesTest {
    public ActivityTestRule<ViewCorrvalues> MyCorrvalueRule = new ActivityTestRule<>(ViewCorrvalues.class,true,false);

    private ViewCorrvalues myActivity = null;


    @Before
    public void setUp() throws Exception {
        myActivity = MyCorrvalueRule.getActivity();

    }

    @Test
    public void testLaunch_viewCorrvalues(){
        //check if the top title "View Location Values" is set up.
        Espresso.onView(withId(R.id.textView4)).check((ViewAssertion) withText("View Location Values"));

        //check if the "Coorvalues" textView is set up.
        Espresso.onView(withId(R.id.Corrvalues));
    }

    @Test
    //Test if the corresponding values table is same as the table fetched.
    public void testCorrvalues_Table(){

        Espresso.onView(withId(R.id.allvalues)).check((ViewAssertion)withText(fetchCorrData.finalValues));
    }


    @After
    public void tearDown() throws Exception {
    }
}