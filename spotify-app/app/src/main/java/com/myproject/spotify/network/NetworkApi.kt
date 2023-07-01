package com.myproject.spotify.network

import com.myproject.spotify.datamodel.Playlist
import com.myproject.spotify.datamodel.Section
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {
    @GET("feed")
    fun getHomeFeed(): Call<List<Section>>

    @GET("playlists/{id}")
    fun getPlaylist(@Path("id") id: Int): Call<Playlist>

}