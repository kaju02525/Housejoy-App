package com.housejoy.ui.fragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.housejoy.R
import kotlinx.android.synthetic.main.fragment_.view.*


class Feautures : Fragment() {

    var result: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            result = bundle.getString("data")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_, container, false)
        setData(v, result)
        return v
    }

    private fun setData(v: View, result: String?) {
        v.tv_details.text = result
    }


}
