package com.holman.test.testtullave.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomUsuario::class], version = 1)
abstract class BaseDatosAplicacion : RoomDatabase() {
    abstract fun usuarioDao() : UsuarioDao

    /*companion object {
        @Volatile
        private var Instance: BaseDatosAplicacion? = null

        *//*fun getDatabase(context: Context): BaseDatosAplicacion {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BaseDatosAplicacion::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }*//*
    }*/
}