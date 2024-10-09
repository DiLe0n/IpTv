package com.hanzeel.iptvandroidstudio
import java.io.Serializable

data class Channel(
    var id: Int,
    var name: String,
    var category: String,
    var link: String,
    var image: String
) : Serializable
