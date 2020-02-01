package com.aleksandrlalekhin.itunestask.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleksandrlalekhin.itunestask.R
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbum
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbumWithTracks
import com.aleksandrlalekhin.itunestask.domain.MainViewModel
import com.aleksandrlalekhin.itunestask.presentation.adapters.TracksAdapter
import com.aleksandrlalekhin.itunestask.presentation.misc.OnFragmentInteractionListener
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_album_details.*

class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var viewModel: MainViewModel

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var album: MusicAlbum
    private val tracksAdapter = TracksAdapter()

    private lateinit var snackBar: Snackbar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        arguments?.getParcelable<MusicAlbum>(ALBUM_ARGS)?.run { album = this } ?: listener?.onBackPressed()
        initViews()
    }

    private fun initViews() {
        snackBar = Snackbar.make(requireView(), R.string.msg_network_unavailable, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.label_go_back) { listener?.onBackPressed() }

        with(albumTracks) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tracksAdapter
        }
    }

    private fun sendQuery() {
        compositeDisposable.add(
            viewModel.getAlbumDetailed(album.id)
                .subscribe(
                    { albumWithTracks -> setAlbumDetails(albumWithTracks) },
                    { snackBar.show() }
                )
        )
    }

    private fun setAlbumDetails(albumWithTracks: MusicAlbumWithTracks) {
        Glide.with(requireContext())
            .load(album.artWork100Url)
            .placeholder(R.drawable.album_cover_placeholder)
            .into(albumDetailsIcon)

        albumArtistName.text = album.artistName
        albumDetailsTitle.text = album.title

        tracksAdapter.tracks = albumWithTracks.tracksList
    }

    override fun onStart() {
        super.onStart()
        sendQuery()
    }

    override fun onStop() {
        super.onStop()
        snackBar.dismiss()
        compositeDisposable.clear()
    }

    companion object {

        const val TAG = "AlbumDetailsFragment"

        @JvmStatic
        fun newInstance(album: MusicAlbum) = AlbumDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ALBUM_ARGS, album)
            }
        }

        private const val ALBUM_ARGS = "ALBUM_ARGS"
    }
}
