package com.aleksandrlalekhin.itunestask.data

import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbum
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbumWithTracks
import com.aleksandrlalekhin.itunestask.data.entities.MusicTrack
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class AlbumWithTracksDeserializer : JsonDeserializer<MusicAlbumWithTracks> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext): MusicAlbumWithTracks {

        return MusicAlbumWithTracks(
            tracksCount = json!!.asJsonObject["resultCount"].asLong - 1,
            albumInfo = context.deserialize(
                json.asJsonObject["results"].asJsonArray[0],
                MusicAlbum::class.java
            ),
            tracksList = mutableListOf<MusicTrack>().apply {
                json.asJsonObject["results"].asJsonArray
                    .filterIndexed { index, _ -> index != 0 }
                    .map { element -> add(context.deserialize(element, MusicTrack::class.java)) }
            }
        )
    }
}
