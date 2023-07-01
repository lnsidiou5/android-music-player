package com.myproject.spotify.datamodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Playlist(
    @SerializedName("id")
    val albumId: String,
    val songs: List<Song>
): Serializable

