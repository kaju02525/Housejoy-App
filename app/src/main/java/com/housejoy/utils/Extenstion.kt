package com.housejoy.utils


import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.housejoy.R
import com.popwoot.carouselbanner.interfaces.CarouselImageFactory
import java.io.IOException
import java.util.*



fun Context.toast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

//Custom Snackbar
fun AppCompatActivity.snackBar(message: String): Snackbar {
    val sb = Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
    sb.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    val textView = sb.view.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(ContextCompat.getColor(this,R.color.colorError))
    sb.show()
    return sb
}

fun Context.hideSoftKeyboard() {
    try {
        val inputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((this as Activity).currentFocus!!.windowToken, 0)
    } catch (e: Exception) {
    }
}


fun ImageView.loadImage(url: String) {
    val circularProgressDrawable =
        CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .fitCenter()
        .into(this)
}

class ImageFactory : CarouselImageFactory {
    override fun onLoadFactory(url: String, view: ImageView) {
        // Glide.with(view).load(url).into(view)
        view.loadImage(url)
    }
}

fun Context.geoCoder(location: Location): String {
    val geocoder = Geocoder(this, Locale.getDefault())
    var result: String? = null
    try {
        val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        if (addressList != null && addressList.size > 0) {
            val address = addressList[0]
            val sb = StringBuilder()
            for (i in 0 until address.maxAddressLineIndex) {
                sb.append(address.getAddressLine(i)).append("\n")
            }
            /*  sb.append(address.locality).append("\n")
              sb.append(address.postalCode).append("\n")
              sb.append(address.countryName)
              result = sb.toString()*/
            return address.locality
        }
    } catch (e: IOException) {
        return ""
    }
    return ""
}

