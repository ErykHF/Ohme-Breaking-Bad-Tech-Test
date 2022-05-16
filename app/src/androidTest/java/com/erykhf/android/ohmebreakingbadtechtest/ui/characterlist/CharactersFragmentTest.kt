package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.erykhf.android.ohmebreakingbadtechtest.EspressoTestingIdlingResource.idlingResource
import com.erykhf.android.ohmebreakingbadtechtest.MainActivity
import com.erykhf.android.ohmebreakingbadtechtest.R

import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class CharactersFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var instrumentalContext: Context


    @Before
    fun setup() {
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun error_Button_IsNotDisplayed_OnAppLaunch() {
        onView(withId(R.id.errorButton)).check(matches(not(isDisplayed())))
    }

    @Test
    fun error_Text_IsNotDisplayed_OnAppLaunch() {
        onView(withId(R.id.errorTextView)).check(matches(not(isDisplayed())))
    }


    @Test
    fun test_ListDisplayed_OnAppLaunch() {
        onView(withId(R.id.list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_List_IsVisible() {
        onView(withId(R.id.list)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_ClickOnAnItemOnRecyclerView() {

        onView(withId(R.id.list))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            )
    }


    @Test
    fun selectListItem_IsDetailFragmentVisible() {

        onView(withId(R.id.list)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.character_name)).check(matches(withText("Walter White")))


    }
}