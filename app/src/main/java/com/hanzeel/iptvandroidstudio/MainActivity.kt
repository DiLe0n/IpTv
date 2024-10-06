package com.hanzeel.iptvandroidstudio

import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var fullscreenButton: ImageButton
    private var isFullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa la vista de reproducción
        playerView = findViewById(R.id.player_view)

        // Inicializa el reproductor
        player = ExoPlayer.Builder(this).build()

        // Asigna el reproductor a la vista
        playerView.player = player

        // Prepara la media
        val videoUrl = "https://channel01-onlymex.akamaized.net/hls/live/2022749/event01/index.m3u8"
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

}