package com.aleksandrlalekhin.itunestask.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MusicTrack(
    @SerializedName("trackId")
    val id: Long,
    @SerializedName("trackName")
    val title: String
): Parcelable
