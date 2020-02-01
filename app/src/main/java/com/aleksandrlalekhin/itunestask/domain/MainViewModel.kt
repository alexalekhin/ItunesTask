package com.aleksandrlalekhin.itunestask.domain

import androidx.lifecycle.ViewModel
import com.aleksandrlalekhin.itunestask.data.Repository
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbum
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbumWithTracks
import io.reactivex.Observable

class MainViewModel : ViewModel() {

    private val repository = Repository()

    fun getAlbums(term: String): Observable<List<MusicAlbum>> = repository.getAlbums(term)

    fun getAlbumDetailed(albumId: Long): Observable<MusicAlbumWithTracks> = repository.getAlbumDetailed(albumId)
}
