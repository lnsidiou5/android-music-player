package com.myproject.spotify

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myproject.spotify.database.DatabaseDao
import com.myproject.spotify.datamodel.Album
import com.myproject.spotify.network.NetworkApi
import com.myproject.spotify.player.PlayerBar
import com.myproject.spotify.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// customized extend AppCompatActivity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "lifecycle"
    @Inject
    lateinit var api: NetworkApi
    @Inject
    lateinit var databaseDao: DatabaseDao
    private val playerViewModel: PlayerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /***Original stuff without fragments
 * setContent {
 *      SpotifyTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    AlbumCover()
//                }
//            }
//        }
***/
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
        NavigationUI.setupWithNavController(navView, navController)
        navView.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, inclusive = false)
            true
        }
        val playerBar = findViewById<ComposeView>(R.id.player_bar)
        playerBar.apply {
            setContent {
                MaterialTheme(colors = darkColors()) {
                    PlayerBar(
                        playerViewModel
                    )
                }
            }
        }
        // Test retrofit
        GlobalScope.launch(Dispatchers.IO) {
            //val api = NetworkModule.provideRetrofit().create(NetworkApi::class.java)
            val response = api.getHomeFeed().execute().body()
            Log.d("Network", response.toString())
        }
        // remember it runs everytime you start the app
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val album = Album(
                    id = 1,
                    name =  "Hexagonal",
                    year = "2008",
                    cover = "https://upload.wikimedia.org/wikipedia/en/6/6d/Leessang-Hexagonal_%28cover%29.jpg",
                    artists = "Lesssang",
                    description = "Leessang (Korean: 리쌍) was a South Korean hip hop duo, composed of Kang Hee-gun (Gary or Garie) and Gil Seong-joon (Gil)"
                )
                databaseDao.favoriteAlbum(album)
            }
        }


    }
}
//
//@Composable
//private fun AlbumCover() {
//    Column {
//        Box(modifier = Modifier.size(160.dp)) {
//            AsyncImage(
//                model = "https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg",
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.FillBounds
//            )
//            Text(
//                text = "Still Fantasy",
//                color = Color.White,
//                modifier = Modifier
//                    .padding(bottom = 4.dp, start = 2.dp)
//                    .align(Alignment.BottomStart),
//            )
//        }
//
//        Text(
//            text = "Jay Chou",
//            modifier = Modifier.padding(top = 4.dp),
//            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
//            color = Color.LightGray,
//        )
//    }
//}
//@Preview(showBackground = true, widthDp = 412, heightDp = 732)
//@Composable
//fun DefaultPreview() {
//    SpotifyTheme {
//        Surface {
//            AlbumCover() // <- this one
//        }
//    }
//}
