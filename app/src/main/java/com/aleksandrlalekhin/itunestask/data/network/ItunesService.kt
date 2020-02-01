package com.aleksandrlalekhin.itunestask.data.network

import com.aleksandrlalekhin.itunestask.data.entities.AlbumsSearchResult
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesService {

    @GET("search?media=music&entity=album&attribute=albumTerm")
    fun getAlbums(@Query("term") term: String): Observable<AlbumsSearchResult>

    @GET("lookup?media=music&entity=song")
    fun getAlbumDetailed(@Query("id") id: Long): Observable<ResponseBody>

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }
}
