package com.holman.test.testtullave.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Query("DELETE FROM roomusuario")
    fun borrarTodo()

    @Query("SELECT * FROM roomusuario")
    fun listarUsuariosBaseDatos(): List<RoomUsuario>

    @Query("SELECT * from roomusuario WHERE documento = :documento")
    fun buscarUsuario(documento: String): List<RoomUsuario>

    @Insert
    fun agregarUsuarios(vararg usuario: RoomUsuario)
}

