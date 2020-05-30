package com.housejoy

import androidx.test.rule.ActivityTestRule
import com.housejoy.ui.HouseListActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ApiResponseTest {
    @get:Rule
    val mHouseActivityTestRule = ActivityTestRule(HouseListActivity::class.java)
    private lateinit var mHouseActivity: HouseListActivity

    @Before
    fun setUp() {
        mHouseActivity=mHouseActivityTestRule.activity
    }

    @Test
    fun getNewsData() {
        mHouseActivity.apiCall()
        Thread.sleep(8000)
        val expectedValue=mHouseActivity.itemsArray[0].elevation
        print("...........$expectedValue")
        Assert.assertEquals("Elevation",expectedValue)
    }

}