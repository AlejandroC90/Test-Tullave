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

    @Query("SELECT * from roomusuario WHERE tipoDocumento = :tipoDocumento AND documento = :documento AND contrasena = :contrasena")
    fun buscarUsuario(tipoDocumento: String, documento: String, contrasena: String): List<RoomUsuario>

    @Query("SELECT * from roomusuario WHERE tipoDocumento = :tipoDocumento AND documento = :documento")
    fun buscarUsuarioRegistro(tipoDocumento: String, documento: String): List<RoomUsuario>

    @Insert
    fun agregarUsuarios(vararg usuario: RoomUsuario)
}

