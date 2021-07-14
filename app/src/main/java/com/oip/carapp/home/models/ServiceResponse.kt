package com.oip.carapp.home.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @SerializedName("id") val id: String,
    @SerializedName("cat_id") val cat_id: String,
    @SerializedName("service_title") val serviceTitle: String,
    @SerializedName("service_subtitle") val serviceSubtitle: String,
    @SerializedName("service_distance") val serviceDistance: String,
    @SerializedName("is_favourite") var isFavourite: String,
    @SerializedName("service_image") val serviceImage: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("is_deleted") val isDeleted: String,
    @SerializedName("service_amount") val serviceAmount: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(cat_id)
        parcel.writeString(serviceTitle)
        parcel.writeString(serviceSubtitle)
        parcel.writeString(serviceDistance)
        parcel.writeString(isFavourite)
        parcel.writeString(serviceImage)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeString(isDeleted)
        parcel.writeString(serviceAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceResponse> {
        override fun createFromParcel(parcel: Parcel): ServiceResponse {
            return ServiceResponse(parcel)
        }

        override fun newArray(size: Int): Array<ServiceResponse?> {
            return arrayOfNulls(size)
        }
    }
}