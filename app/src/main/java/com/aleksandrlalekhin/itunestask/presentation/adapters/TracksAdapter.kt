package com.aleksandrlalekhin.itunestask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksandrlalekhin.itunestask.R
import com.aleksandrlalekhin.itunestask.data.entities.MusicTrack
import kotlinx.android.synthetic.main.item_track.view.*

class TracksAdapter(tracks: List<MusicTrack> = listOf()) : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    var tracks: List<MusicTrack> = tracks
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(itemView)
    }

    override fun getItemCount() = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) = holder.bind(tracks[position])

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(track: MusicTrack) {
            itemView.trackName.text = track.title
        }
    }
}
