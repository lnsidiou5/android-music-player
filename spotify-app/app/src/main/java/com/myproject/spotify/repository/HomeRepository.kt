package com.myproject.spotify.repository

import com.myproject.spotify.datamodel.Section
import com.myproject.spotify.network.NetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(private val networkApi: NetworkApi) {
    suspend fun getHomeSections(): List<Section> = withContext(Dispatchers.IO) {
        delay(1000)
        networkApi.getHomeFeed().execute().body()!!
    }
}