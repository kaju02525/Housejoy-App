package com.housejoy.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.housejoy.R
import com.housejoy.model.Project
import com.housejoy.ui.fragements.Amenities
import com.housejoy.ui.fragements.Feautures
import com.housejoy.ui.fragements.Introduction
import com.housejoy.utils.loadImage
import com.housejoy.utils.toast
import kotlinx.android.synthetic.main.activity_details.*


class HouseDetailsActivity : AppCompatActivity() {

    var title = arrayOf<CharSequence>("Introduction", "Features", "Amenities")
    var projectModel: Project? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initView()
    }

    private fun initView() {
        projectModel = intent.getParcelableExtra("project") as Project?


        projectModel?.let {
            iv_bg.loadImage(projectModel?.imageUrl!!)
            tvProjectName.text=projectModel?.projectName
            tvSqft.text=projectModel?.area
            tvBHK.text=projectModel?.config!!
            tvGround.text=projectModel?.elevation
        }
        btnMenu.setOnClickListener {
            toast("Empty Menu") }
        iv_back.setOnClickListener { onBackPressed() }


        setUpTabs()
    }


    private fun setUpTabs() {
        pager!!.adapter = MainPagerAdapter(this.supportFragmentManager, title, title.size)
        tabs!!.setupWithViewPager(pager)
    }

    inner class MainPagerAdapter(
        fm: FragmentManager,
        private var titles: Array<CharSequence>,
        var numbOfTabs: Int
    ) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    val f = Introduction()
                    val bundle = Bundle()
                    bundle.putString("data",projectModel?.introduction)
                    f.arguments = bundle
                    return f
                }
                1 -> {
                    val f = Feautures()
                    val bundle = Bundle()
                    bundle.putString("data", projectModel?.feautures)
                    f.arguments = bundle
                    return f
                }
                else -> {
                    val f = Amenities()
                    val bundle = Bundle()
                    bundle.putString("data",projectModel?.amenities)
                    f.arguments = bundle
                    return f
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }

        override fun getCount(): Int {
            return numbOfTabs
        }
    }
    fun btnEnquire(view: View) {toast("Please wait...")}
}
