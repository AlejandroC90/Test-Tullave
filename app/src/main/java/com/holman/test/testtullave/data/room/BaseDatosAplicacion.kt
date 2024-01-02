package com.holman.test.testtullave.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomUsuario::class, RoomTarjeta::class], version = 1)
abstract class BaseDatosAplicacion : RoomDatabase() {
    abstract fun usuarioDao() : UsuarioDao

    abstract fun tarjetaDao() : TarjetaDao
}