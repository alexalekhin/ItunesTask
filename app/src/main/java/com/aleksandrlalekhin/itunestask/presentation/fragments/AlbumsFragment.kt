package com.aleksandrlalekhin.itunestask.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleksandrlalekhin.itunestask.R
import com.aleksandrlalekhin.itunestask.domain.MainViewModel
import com.aleksandrlalekhin.itunestask.presentation.adapters.AlbumsAdapter
import com.aleksandrlalekhin.itunestask.presentation.misc.OnFragmentInteractionListener
import com.aleksandrlalekhin.itunestask.presentation.misc.hideKeyboard
import com.aleksandrlalekhin.itunestask.presentation.misc.showKeyboard
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_albums.*
import java.util.concurrent.TimeUnit

class AlbumsFragment : Fragment(R.layout.fragment_albums), AlbumsAdapter.OnAlbumClickListener {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var viewModel: MainViewModel

    private var listener: OnFragmentInteractionListener? = null

    private val albumsAdapter = AlbumsAdapter(
        clickListener = this
    )

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
        initViews()
    }

    private fun initViews() {
        snackBar = Snackbar.make(requireView(), R.string.msg_network_unavailable, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.label_retry) { sendQuery() }

        with(albumsRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = albumsAdapter
        }

        searchTerm.showKeyboard()
    }

    private fun sendQuery() {
        compositeDisposable.add(
            searchTerm.textChangeEvents()
                .debounce(USER_INPUT_DELAY, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter { textEvent -> textEvent.text.isNotEmpty() && textEvent.text.isNotBlank() }
                .switchMap { textEvent -> viewModel.getAlbums(textEvent.text.toString()) }
                .subscribe(
                    { albums -> albumsAdapter.albums = albums },
                    { snackBar.show() }
                )
        )
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

    override fun onAlbumClick(position: Int) {
        listener?.gotoAlbumDetails(albumsAdapter.albums[position])
        searchTerm.hideKeyboard()
    }

    companion object {

        private const val USER_INPUT_DELAY = 1000L

        const val TAG = "AlbumsFragment"

        fun newInstance() = AlbumsFragment()
    }
}
