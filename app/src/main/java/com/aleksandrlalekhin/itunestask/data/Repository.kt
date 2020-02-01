package com.aleksandrlalekhin.itunestask.data

import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbum
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbumWithTracks
import com.aleksandrlalekhin.itunestask.data.network.ItunesService
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    private val itunesService: ItunesService = Retrofit.Builder()
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ItunesService.BASE_URL)
        .build().run {
            create(ItunesService::class.java)
        }

    fun getAlbums(term: String): Observable<List<MusicAlbum>> {
        return itunesService.getAlbums(term)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { albumsSearchResult -> albumsSearchResult.results.sortedBy { album -> album.title } }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAlbumDetailed(albumId: Long): Observable<MusicAlbumWithTracks> {
        return itunesService.getAlbumDetailed(albumId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(::parseTracks)
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun parseTracks(body: ResponseBody): MusicAlbumWithTracks {
        val gson = GsonBuilder()
            .registerTypeAdapter(MusicAlbumWithTracks::class.java,
                AlbumWithTracksDeserializer()
            )
            .create()

        return gson.fromJson(body.string(), MusicAlbumWithTracks::class.java)
    }
}
