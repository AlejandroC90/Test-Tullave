package com.holman.test.testtullave.data.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TarjetaDao {
    @Query("DELETE FROM roomtarjeta WHERE numero = :numero")
    fun borrarTarjeta(numero: String)

    @Query("SELECT * FROM roomtarjeta where usuario = :usuario")
    fun listarTarjetas(usuario: String): List<RoomTarjeta>

    @Query("SELECT * FROM roomtarjeta where numero = :numero")
    fun buscarTarjeta(numero: String): List<RoomTarjeta>

    @Insert
    fun agregarTarjeta(vararg tarjeta: RoomTarjeta)
}

