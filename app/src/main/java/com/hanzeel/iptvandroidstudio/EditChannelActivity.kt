package com.hanzeel.iptvandroidstudio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import java.io.Serializable

class EditChannelActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextCategory: EditText
    private lateinit var editTextLink: EditText
    private lateinit var editTextImageLink: EditText // Nuevo campo para el link de la imagen
    private lateinit var buttonUpdate: Button
    private lateinit var buttonDelete: Button
    private lateinit var buttonPlayChannel: Button
    private lateinit var player: ExoPlayer
    private var selectedChannel: Channel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_channel)

        // Configuración de las vistas
        editTextName = findViewById(R.id.editTextName)
        editTextCategory = findViewById(R.id.editTextCategory)
        editTextLink = findViewById(R.id.editTextLink)
        editTextImageLink = findViewById(R.id.editTextImageLink) // Asignar el nuevo campo

        buttonUpdate = findViewById(R.id.buttonUpdate)
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonPlayChannel = findViewById(R.id.buttonPlayChannel)

        // Obtener el canal pasado desde MainActivity
        selectedChannel = intent.getSerializableExtra("channel") as? Channel

        if (selectedChannel != null) {
            // Inicializar la información del canal en los campos de edición
            editTextName.setText(selectedChannel?.name)
            editTextCategory.setText(selectedChannel?.category)
            editTextLink.setText(selectedChannel?.link)
            editTextImageLink.setText(selectedChannel?.image) // Mostrar el link de la imagen en el campo
        } else {
            Toast.makeText(this, "Error: No se pudo obtener el canal seleccionado", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Configurar el reproductor
        player = ExoPlayer.Builder(this).build()

        buttonUpdate.setOnClickListener {
            updateChannel()
        }

        buttonDelete.setOnClickListener {
            deleteChannel()
        }

        buttonPlayChannel.setOnClickListener {
            playChannel()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editChannel)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateChannel() {
        selectedChannel?.let { channel ->
            // Actualizar los datos del canal
            channel.name = editTextName.text.toString()
            channel.category = editTextCategory.text.toString()
            channel.link = editTextLink.text.toString()
            channel.image = editTextImageLink.text.toString() // Actualizar también el link de la imagen

            val intent = Intent().apply {
                putExtra("updatedChannel", channel as Serializable) // Pasa el canal actualizado como Serializable
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun deleteChannel() {
        selectedChannel?.let {
            val intent = Intent().apply {
                putExtra("deletedChannel", it as Serializable)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun playChannel() {
        selectedChannel?.let {
            val mediaItem = MediaItem.fromUri(Uri.parse(it.link))
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()

            Toast.makeText(this, "Reproduciendo: ${it.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}
