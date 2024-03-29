package com.holman.test.testtullave.data

import android.content.Context
import androidx.room.Room
import com.holman.test.testtullave.data.room.BaseDatosAplicacion
import com.holman.test.testtullave.data.room.RoomUsuario
import com.holman.test.testtullave.data.room.UsuarioDao

class RepositorioUsuarios {

    /**
     * Funcion que permite a un usuario iniciar sesión usando los datos de registro
     */
    fun iniciarSesion(context: Context, tipoDocumento: String, documento: String, contrasena: String): Boolean{
        var bd = interfazDBParaUsuarios(context)

        var listado = bd.buscarUsuario( tipoDocumento, documento, contrasena)

        return !listado.isEmpty()
    }


    /**
     * Funcion que permite registrar un usuario a la base de datos local
     */
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
            var usuarioActual = bd.buscarUsuarioRegistro(tipoDocumento, numeroDocumento)

            //el usuario no existe
            if(usuarioActual.isEmpty()){
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
            }
            false
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