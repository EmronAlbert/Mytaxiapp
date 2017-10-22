package com.mytaxiapp.ui.listvehicles


import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.ViewInteraction
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import com.mytaxiapp.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.util.concurrent.TimeUnit

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.allOf

@LargeTest
@RunWith(AndroidJUnit4::class)
class MapFragmentTest {

    private var idlingResource: IdlingResource? = null

    @Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mapFragmentTest() {

        // The recommended way to handle such scenarios is to use Espresso idling resources:
        waitFor(5000) // wait for UI to be properly loaded

        val recyclerView = onView(
                allOf(withId(R.id.vehicles_recyclerview),
                        withParent(allOf(withId(R.id.main_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()))
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // The recommended way to handle such scenarios is to use Espresso idling resources:
        waitFor(5000) // wait for UI to be properly loaded

        val textView = onView(
                allOf(withId(R.id.content), withText("HH-GO8522"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        0),
                                1),
                        isDisplayed()))
        textView.check(matches(withText("HH-GO8522")))

        val textView2 = onView(
                allOf(withId(R.id.content), withText("HH-GO8522"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        0),
                                1),
                        isDisplayed()))
        textView2.check(matches(withText("HH-GO8522")))

        val textView3 = onView(
                allOf(withId(R.id.content), withText("HH-GO8480"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        1),
                                1),
                        isDisplayed()))
        textView3.check(matches(withText("HH-GO8480")))

        val recyclerView2 = onView(
                allOf(withId(R.id.item_list), isDisplayed()))
        recyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        // The recommended way to handle such scenarios is to use Espresso idling resources:
        waitFor(5000) // wait for UI to be properly loaded

        val textView4 = onView(
                allOf(withId(R.id.content), withText("HH-GO8001"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list),
                                        2),
                                1),
                        isDisplayed()))
        textView4.check(matches(withText("HH-GO8001")))

    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }


    }

    fun waitFor(waitingTime: Long) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS)
        // Now we wait
        idlingResource = ElapsedTimeIdlingResource(waitingTime)
        Espresso.registerIdlingResources(idlingResource!!)
    }

    fun cleanUp() {
        // Clean up
        Espresso.unregisterIdlingResources(idlingResource!!)
    }
}
