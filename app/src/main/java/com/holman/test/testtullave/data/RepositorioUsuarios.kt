package com.holman.test.testtullave.data

import android.content.Context
import androidx.room.Room
import com.holman.test.testtullave.data.room.BaseDatosAplicacion
import com.holman.test.testtullave.data.room.RoomUsuario
import com.holman.test.testtullave.data.room.UsuarioDao

class RepositorioUsuarios {

    fun iniciarSesion(context: Context, documento: String, contrasena: String): Boolean{
        var bd = interfazDBParaUsuarios(context)

        var listado = bd.buscarUsuario(documento, contrasena)

        return !listado.isEmpty()
    }


    fun registrarUsuario(context: Context,
                         nombres: String,
                         apellidos: String,
                         direccion: String,
                         correo: String,
                         tipoDocumento: String,
                         numeroDocumento: String,
                         contrasena: String): Boolean{
        var bd = interfazDBParaUsuarios(context)

        return try {
            bd.agregarUsuarios(RoomUsuario(
                nombres = nombres,
                apellidos = apellidos,
                documento = numeroDocumento,
                tipoDocumento = tipoDocumento,
                contrasena = contrasena,
                correo = correo,
                direccion = direccion
            ))
            true
        }catch (e : Exception){
            false
        }
    }

    /**
     * Acceso a base de datos de usuarios
     */
    fun interfazDBParaUsuarios(context: Context): UsuarioDao {
        val db = Room.databaseBuilder(
            context,
            BaseDatosAplicacion::class.java, "usuarios"
        ).allowMainThreadQueries().build()

        return db.usuarioDao()
    }
}