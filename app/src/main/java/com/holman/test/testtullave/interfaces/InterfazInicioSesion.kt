package com.holman.test.testtullave.interfaces
import android.content.Context

/**
 * Interfaz usada para las clases que permiten la ejecucion del inicio de sesión
 */
interface InterfazInicioSesion {

    interface Vista {
        fun mostrarCargando()
        fun ocultarCargando()
        fun resultadoInicioSesion(resultado: Boolean)

    }

    interface Presentador {
        fun iniciarSesion(context: Context, tipoDocumento :String, documento: String, contrasena: String)
    }

    interface Modelo {
        suspend fun iniciarSesion(context: Context, tipoDocumento :String, documento: String, contrasena: String): Boolean
    }
}