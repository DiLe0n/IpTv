package com.hanzeel.iptvandroidstudio

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtiene los canales y configura el adaptador
        val channels = getChannels()
        recyclerAdapter = RecyclerAdapter(channels)
        recyclerView.adapter = recyclerAdapter

        Log.d("ChannelList", "Tamaño de la lista: ${channels.size}")

        // Inicializa la vista de reproducción
        playerView = findViewById(R.id.player_view)

        // Inicializa el reproductor
        player = ExoPlayer.Builder(this).build()

        // Asigna el reproductor a la vista
        playerView.player = player

        // Prepara la media
        val videoUrl = "https://canadaremar2.todostreaming.es/live/peque-pequetv.m3u8"
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player.setMediaItem(mediaItem)

        // Prepara y empieza la reproducción
        player.prepare()
        player.play()
    }

    override fun onStop() {
        super.onStop()
        // Libera el reproductor cuando no sea necesario
        player.release()
    }

    private fun getChannels() : MutableList<Channel> {
        val channels:MutableList<Channel> = ArrayList()

        channels.add(Channel("Pequeradio TV", "Infantil", "https://canadaremar2.todostreaming.es/live/peque-pequetv.m3u8", "https://static.wixstatic.com/media/76b12f_b725806aac4c416da697ccf6a5c6dd83~mv2.png/v1/fit/w_2500,h_1330,al_c/76b12f_b725806aac4c416da697ccf6a5c6dd83~mv2.png"))
        channels.add(Channel("Red bull TV", "Deportes", "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8", "https://image.roku.com/developer_channels/prod/0560cd3757ba28d0525e524fe0d98c1b95721c5ecf5fda464eba3084eeed7f36.png"))
        channels.add(Channel("AKC TV Dogs", "Animales", "https://install.akctvcontrol.com/speed/broadcast/138/desktop-playlist.m3u8", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLPq9IAKaYCAvNxg2AWKukgyAtvBbMa9GVZQ&s"))

        return channels
    }

}