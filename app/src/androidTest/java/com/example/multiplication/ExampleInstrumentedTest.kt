package com.example.multiplication

import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun btn_multi() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(R.id.btn_multi),
                withText(R.string.multi),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).check(PositionAssertions.isCompletelyBelow(withText(R.string.table_title)))

        onView(withId(R.id.btn_multi)).perform(click())

        onView(
            isAssignableFrom(ConstraintLayout::class.java)
        ).check(doesNotExist())

        onView(
            allOf(
                isAssignableFrom(LinearLayout::class.java),
                withId(R.id.main),
            )
        ).check(matches(isDisplayed()))

        pressBack()

        onView(
            isAssignableFrom(ConstraintLayout::class.java)
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                isAssignableFrom(LinearLayout::class.java),
                withId(R.id.main),
            )
        ).check(doesNotExist())
    }
}