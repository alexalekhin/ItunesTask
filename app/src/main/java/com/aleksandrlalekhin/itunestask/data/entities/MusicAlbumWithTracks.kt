package com.aleksandrlalekhin.itunestask.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicAlbumWithTracks(
    val tracksCount: Long,
    val albumInfo: MusicAlbum,
    val tracksList: List<MusicTrack>
): Parcelable
