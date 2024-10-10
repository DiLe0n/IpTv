package com.hanzeel.iptvandroidstudio

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class EditChannelActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextCategory: EditText
    private lateinit var editTextLink: EditText
    private lateinit var editTextImage: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonDelete: Button
    private lateinit var channel: Channel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_channel)

        // Obtener el canal pasado desde MainActivity
        channel = intent.getSerializableExtra("channel") as Channel

        // Inicializar vistas
        editTextName = findViewById(R.id.editTextName)
        editTextCategory = findViewById(R.id.editTextCategory)
        editTextLink = findViewById(R.id.editTextLink)
        editTextImage = findViewById(R.id.editTextImage)
        buttonSave = findViewById(R.id.buttonSave)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Llenar los EditText con la información del canal
        editTextName.setText(channel.name)
        editTextCategory.setText(channel.category)
        editTextLink.setText(channel.link)
        editTextImage.setText(channel.image)

        // Configurar el botón de guardar
        buttonSave.setOnClickListener {
            saveChannel()
        }

        // Configurar el botón de eliminar
        buttonDelete.setOnClickListener {
            deleteChannel()
        }
    }

    // Método para guardar el canal modificado
    private fun saveChannel() {
        val updatedChannel = Channel(
            id = channel.id,
            name = editTextName.text.toString(),
            category = editTextCategory.text.toString(),
            link = editTextLink.text.toString(),
            image = editTextImage.text.toString()
        )

        val resultIntent = Intent()
        resultIntent.putExtra("updatedChannel", updatedChannel as Serializable)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()  // Cierra la actividad
    }

    // Método para eliminar el canal
    private fun deleteChannel() {
        val resultIntent = Intent()
        resultIntent.putExtra("deletedChannel", channel as Serializable)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()  // Cierra la actividad
    }
}