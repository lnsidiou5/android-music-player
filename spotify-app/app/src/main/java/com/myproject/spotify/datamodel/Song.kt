package com.myproject.spotify.datamodel

import java.io.Serializable

data class Song(
    val name: String,
    val lyric: String,
    val src: String,
    val length: String
): Serializable