package com.aleksandrlalekhin.itunestask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksandrlalekhin.itunestask.R
import com.aleksandrlalekhin.itunestask.data.entities.MusicAlbum
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumsAdapter(albums: List<MusicAlbum> = listOf(), private val clickListener: OnAlbumClickListener? = null) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    var albums: List<MusicAlbum> = albums
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(
            itemView,
            clickListener
        )
    }

    override fun getItemCount() = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) = holder.bind(albums[position])

    class AlbumViewHolder(itemView: View, private val clickListener: OnAlbumClickListener?) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bind(album: MusicAlbum) {
            Glide.with(itemView)
                .load(album.artWork60Url)
                .placeholder(R.drawable.album_cover_placeholder)
                .into(itemView.albumCover)
            itemView.albumTitle.text = album.title
            itemView.albumArtist.text = album.artistName
        }

        init {
            itemView.albumRoot.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener?.onAlbumClick(adapterPosition)
        }
    }

    interface OnAlbumClickListener {

        fun onAlbumClick(position: Int)
    }
}
