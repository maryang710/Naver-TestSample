package com.maryang.testsample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maryang.testsample.ui.signup.SignupActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SingupActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SignupActivity::class.java)

    @Test
    fun test() {
        onView(withId(R.id.signupButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.checkbox1)).perform(click())
        onView(withId(R.id.checkbox2)).perform(click())
        onView(withId(R.id.checkbox3)).perform(click())
        onView(withId(R.id.signupButton)).check(matches(isEnabled()))
    }
}
