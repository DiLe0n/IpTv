package com.hanzeel.iptvandroidstudio

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var channelAdapter: RecyclerAdapter
    private var channelList: MutableList<Channel> = mutableListOf()

    // Registrar el ActivityResultLauncher para manejar el resultado del EditChannelActivity
    private val editChannelLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val updatedChannel = result.data?.getSerializableExtra("updatedChannel") as? Channel
            updatedChannel?.let {
                updateChannelInList(it)
            }

            // Verificar si se eliminó un canal
            val deletedChannel = result.data?.getSerializableExtra("deletedChannel") as? Channel
            deletedChannel?.let {
                deleteChannelFromList(it)
            }
        }
    }

    // Método para eliminar el canal de la lista
    private fun deleteChannelFromList(deletedChannel: Channel) {
        val index = channelList.indexOfFirst { it.id == deletedChannel.id }
        if (index != -1) {
            channelList.removeAt(index)
            channelAdapter.notifyItemRemoved(index)
            Toast.makeText(this, "Canal eliminado: ${deletedChannel.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar RecyclerView y el adaptador
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar la lista de canales
        channelAdapter = RecyclerAdapter(channelList) { selectedChannel ->
            openEditChannelActivity(selectedChannel)
        }
        recyclerView.adapter = channelAdapter

        // Cargar la lista de canales (esto es solo un ejemplo, puede ser desde una base de datos o API)
        loadChannels()
    }

    // Función para abrir la actividad de edición
    private fun openEditChannelActivity(channel: Channel) {
        val intent = Intent(this, EditChannelActivity::class.java)
        intent.putExtra("channel", channel as Serializable) // Pasar el canal seleccionado como Serializable
        editChannelLauncher.launch(intent)  // Lanzar la actividad y esperar el resultado
    }

    // Actualizar la lista con el canal modificado
    // Método para actualizar el canal en la lista
    private fun updateChannelInList(updatedChannel: Channel) {
        val index = channelList.indexOfFirst { it.id == updatedChannel.id } // Usar id en lugar de name
        if (index != -1) {
            channelList[index] = updatedChannel
            channelAdapter.notifyItemChanged(index)
            Toast.makeText(this, "Canal actualizado: ${updatedChannel.name}", Toast.LENGTH_SHORT).show()
        }
    }

    // Cargar canales (ejemplo estático, podrías traer los canales desde una base de datos)
    private fun loadChannels() {
        // Cargar algunos canales de ejemplo
        channelList.add(Channel(1, "Pequeradio TV", "Infantil", "https://canadaremar2.todostreaming.es/live/peque-pequetv.m3u8", "https://static.wixstatic.com/media/76b12f_b725806aac4c416da697ccf6a5c6dd83~mv2.png/v1/fit/w_2500,h_1330,al_c/76b12f_b725806aac4c416da697ccf6a5c6dd83~mv2.png"))
        channelList.add(Channel(2, "Red bull TV", "Deportes", "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8", "https://image.roku.com/developer_channels/prod/0560cd3757ba28d0525e524fe0d98c1b95721c5ecf5fda464eba3084eeed7f36.png"))
        channelList.add(Channel(3, "AKC TV Dogs", "Animales", "https://install.akctvcontrol.com/speed/broadcast/138/desktop-playlist.m3u8", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLPq9IAKaYCAvNxg2AWKukgyAtvBbMa9GVZQ&s"))
        channelAdapter.notifyDataSetChanged() // Notificar cambios al adaptador
    }
}
