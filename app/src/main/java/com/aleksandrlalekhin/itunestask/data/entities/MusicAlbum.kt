package com.aleksandrlalekhin.itunestask.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicAlbum(
    @SerializedName("collectionId")
    val id: Long,
    @SerializedName("collectionName")
    val title: String,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artworkUrl60")
    val artWork60Url: String,
    @SerializedName("artworkUrl100")
    val artWork100Url: String,
    @SerializedName("trackCount")
    val trackCount: Long
): Parcelable
