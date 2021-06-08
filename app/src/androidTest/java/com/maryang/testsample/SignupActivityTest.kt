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


@RunWith(AndroidJUnit4::class)
class SignupActivityTest {

    // 여기서 SignupActivity가 바로 생성됨
    // SIgnupActivity 내부에서 ViewModel이 바로 생성됨
    // Mocking할 새가 없네?
    @get:Rule
    val activity = ActivityScenarioRule(SignupActivity::class.java)

    // Hilt는 앱이 실행되면 의존성을 생성해서 주입 해준다.
    // TestHiltModule로 앱이 동작하게 하면 Activity가 생성될 때 테스트 객체들을 주입할 수 있게 된다.

    @Test
    fun launchTest() {
        onView(withId(R.id.signupButton)).check(matches(not(isEnabled()))) // isEnable이 아닌지 체크
        onView(withId(R.id.checkbox1)).perform(click()) // checkbox1을 눌러라
        onView(withId(R.id.checkbox2)).perform(click())
        onView(withId(R.id.checkbox3)).perform(click())
        onView(withId(R.id.signupButton)).check(matches(isEnabled())) // isEnable인지 체크
    }
}
