package com.housejoy.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ApiResponse(
    @SerializedName("banner")
    var banner: List<String> = listOf(),
    @SerializedName("projectList")
    var projectList: List<Project> = listOf(),
    @SerializedName("status")
    var status: Int = 0
)

@Parcelize
data class Project(
    @SerializedName("Amenities")
    var amenities: String = "",
    @SerializedName("area")
    var area: String = "",
    @SerializedName("config")
    var config: String = "",
    @SerializedName("Elevation")
    var elevation: String = "",
    @SerializedName("Feautures")
    var feautures: String = "",
    @SerializedName("imageUrl")
    var imageUrl: String = "",
    @SerializedName("Introduction")
    var introduction: String = "",
    @SerializedName("projectLocation")
    var projectLocation: String = "",
    @SerializedName("projectName")
    var projectName: String = ""
): Parcelable