package com.mytaxiapp.ui.listvehicles;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mytaxiapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MapFragmentTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mapFragmentTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.vehicles_recyclerview),
                        withParent(allOf(withId(R.id.main_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        // The recommended way to handle such scenarios is to use Espresso idling resources:
        waitFor(5000); // wait for UI to be properly loaded

        ViewInteraction textView = onView(
                allOf(withId(R.id.content), withText("HH-GO8522"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("HH-GO8522")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.content), withText("HH-GO8522"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("HH-GO8522")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.content), withText("HH-GO8480"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        1),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("HH-GO8480")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.item_list), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        // The recommended way to handle such scenarios is to use Espresso idling resources:
        waitFor(5000); // wait for UI to be properly loaded

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.content), withText("HH-GO8001"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        2),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("HH-GO8001")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };


    }

    public void waitFor(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        // Now we wait
        idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);
    }

    public void cleanUp(){
        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);
    }
}
