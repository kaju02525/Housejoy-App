package com.housejoy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.housejoy.adapter.HouseAdapter
import com.housejoy.ui.HouseListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HouseListActivityTest {
     @get:Rule
     val mActivityTestRule = ActivityTestRule(HouseListActivity::class.java)
     private lateinit var mHouseActivity:HouseListActivity

    @Before
    fun setUp() {
        mHouseActivity=mActivityTestRule.activity

    }

    @Test
    fun houseListScroll() {
        mHouseActivity.apiCall()
        Thread.sleep(8000)
        testMultipleTimes(1)
        testMultipleTimes(3)
    }

    private fun testMultipleTimes(position: Int) {
        onView(withId(R.id.rv_list)).perform(actionOnItemAtPosition<HouseAdapter.ViewHolder>(position,click()))
        Thread.sleep(3000)

        onView(withId(R.id.iv_back)).perform(click())
        Thread.sleep(2000)
    }

}