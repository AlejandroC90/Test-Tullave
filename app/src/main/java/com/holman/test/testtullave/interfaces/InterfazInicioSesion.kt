package com.holman.test.testtullave.interfaces
import android.content.Context

/**
 * Contrato para todas la aplicacion, se requiere de cargar usuarios y cargar post por usuario
 */
interface InterfazInicioSesion {

    interface Vista {
        fun mostrarCargando()
        fun ocultarCargando()
        fun resultadoInicioSesion(resultado: Boolean)

    }

    interface Presentador {
        fun iniciarSesion(context: Context, documento: String, contrasena: String)
    }

    interface Modelo {
        suspend fun iniciarSesion(context: Context, documento: String, contrasena: String): Boolean
    }
}