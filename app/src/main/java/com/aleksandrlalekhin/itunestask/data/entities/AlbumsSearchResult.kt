package com.aleksandrlalekhin.itunestask.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumsSearchResult (
    @SerializedName("resultCount")
    val resultCount: Long,
    @SerializedName("results")
    val results: List<MusicAlbum>
): Parcelable
