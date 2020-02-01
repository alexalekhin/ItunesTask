package com.aleksandrlalekhin.itunestask.presentation.misc

import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbum

interface OnFragmentInteractionListener {

    fun gotoAlbumDetails(album: MusicAlbum)

    fun onBackPressed()
}
