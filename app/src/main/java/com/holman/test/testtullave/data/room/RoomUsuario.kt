package com.holman.test.testtullave.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomUsuario (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nombres: String? = null,
    var apellidos: String? = null,
    var documento: String? = null,
    var direccion: String? = null,
    var correo: String? = null,
    var contrasena: String? = null

)