package com.housejoy.ui

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.housejoy.R
import com.housejoy.adapter.HouseAdapter
import com.housejoy.model.Project
import com.housejoy.mvvm.HouseListViewModel
import com.housejoy.utils.ImageFactory
import com.housejoy.utils.geoCoder
import com.housejoy.utils.snackBar
import com.housejoy.utils.toast
import com.popwoot.carouselbanner.Banner
import com.winds.smartlocationgps.SmartLocation
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class HouseListActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    var itemsArray = mutableListOf<Project>()
    private val viewModel: HouseListViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getNewsData().observe(this, Observer {
            if (it != null) {
                progressHideShow(false)
                itemsArray.clear()
                itemsArray = it.projectList as MutableList<Project>
                bannerData(it.banner)
                setAdapter()
            }
        })

        viewModel.getErrorMessage().observe(this, Observer {
            progressHideShow(false)
            snackBar(it)
        })
    }

    private fun bannerData(bannerList: List<String>) {
        Banner.init(ImageFactory())
        banner.initBanner(bannerList)
    }

    private fun setAdapter() {
        rv_list.adapter = HouseAdapter(itemsArray)
    }


    private fun progressHideShow(flag: Boolean) {
        if (flag) {
            banner.visibility = View.VISIBLE
            progress.visibility = View.VISIBLE
            tv_title.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
            banner.visibility = View.GONE
            tv_title.visibility = View.GONE
        }
    }


    //Location Update
    override fun onStart() {
        startUpdates()
        super.onStart()
    }

    override fun onStop() {
        SmartLocation.stopLocationUpdates()
        super.onStop()
    }


    private fun startUpdates() {
        SmartLocation.configure {
            shouldResolveRequest = true
        }
        SmartLocation.startLocationUpdates(this) { result ->
            result.location?.let(::onLocationUpdate)
            result.error?.let(::onError)
        }
    }

    private fun onLocationUpdate(location: Location) {
        if (location.latitude != null && location.longitude != null) {
            apiCall()
            tvLocation.text = geoCoder(location)
        }
    }

    fun apiCall(){
        viewModel.callHouseListApi()
    }

    private fun onError(error: Throwable?) {
        //finish()
        progressHideShow(false)
        toast("Please enable location permissions in device settings ")
        Log.d("TAG", "onError: " + error?.message)
    }
}

