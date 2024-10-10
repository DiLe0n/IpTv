package com.hanzeel.iptvandroidstudio

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddChannelActivity : AppCompatActivity() {  // Verifica que el nombre de la clase es AddChannelActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_channel)

        // Implementación de la lógica de la actividad para agregar canales
        val nameEditText = findViewById<EditText>(R.id.et_channel_name)
        val categoryEditText = findViewById<EditText>(R.id.et_channel_category)
        val linkEditText = findViewById<EditText>(R.id.et_channel_link)
        val imageEditText = findViewById<EditText>(R.id.et_channel_image)
        val saveButton = findViewById<Button>(R.id.btn_save_channel)

        saveButton.setOnClickListener {
            val channel = Channel(
                id = System.currentTimeMillis().toInt(), // Generar un ID único
                name = nameEditText.text.toString(),
                category = categoryEditText.text.toString(),
                link = linkEditText.text.toString(),
                image = imageEditText.text.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra("newChannel", channel)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Cerrar la actividad
        }
    }
}
