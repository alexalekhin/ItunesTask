package com.aleksandrlalekhin.itunestask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.aleksandrlalekhin.itunestask.R
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbum
import com.aleksandrlalekhin.itunestask.presentation.fragments.AlbumDetailsFragment
import com.aleksandrlalekhin.itunestask.presentation.fragments.AlbumsFragment
import com.aleksandrlalekhin.itunestask.presentation.misc.OnFragmentInteractionListener

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AlbumsFragment.newInstance(), AlbumsFragment.TAG)
                .commit()
        }
    }

    override fun gotoAlbumDetails(album: MusicAlbum) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AlbumDetailsFragment.newInstance(album), AlbumDetailsFragment.TAG)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(AlbumDetailsFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
