package com.holman.test.testtullave.data.room
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomTarjeta (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var numero: String? = null,
    var usuario: String? = null,
)