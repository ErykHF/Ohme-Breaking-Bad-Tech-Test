package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onIdle
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
import com.erykhf.android.ohmebreakingbadtechtest.EspressoTestingIdlingResource.decrement
import com.erykhf.android.ohmebreakingbadtechtest.EspressoTestingIdlingResource.idlingResource
import com.erykhf.android.ohmebreakingbadtechtest.EspressoTestingIdlingResource.increment
import com.erykhf.android.ohmebreakingbadtechtest.MainActivity
import com.erykhf.android.ohmebreakingbadtechtest.R
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class CharactersFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        // let espresso know to synchronize with background tasks
        IdlingRegistry.getInstance().register(idlingResource)

    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }


    val LIST_ITEM_TEST = 0
    val CHARACTER_IN_TEST = BreakingBadCharacterItem(listOf())


    @Test
    fun test_iSpinnerVisible_OnAppLaunch() {
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isListFragMentVisible_OnAppLaunch() {
        increment()
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        decrement()
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        // Attempt to scroll to an item that contains the special text.
        onView(withId(R.id.list))
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("not in the list"))
                )
            )
    }

    @Test
    fun selectListItem_IsDetailFragmentVisible() {

        onView(withId(R.id.list))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(LIST_ITEM_TEST, click()))

        onView(withId(R.id.character_name)).check(matches(withText("Walter White")))

    }

}