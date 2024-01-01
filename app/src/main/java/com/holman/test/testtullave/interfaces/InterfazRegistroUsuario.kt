package com.holman.test.testtullave.interfaces

import android.content.Context

interface InterfazRegistroUsuario {

    interface Vista {
        fun mostrarCargando()
        fun ocultarCargando()
        fun resultadoRegistro(resultado: Boolean)

    }

    interface Presentador {
        fun registrarUsuario(
            context: Context,
            nombres: String,
            apellidos: String,
            direccion: String,
            correo: String,
            tipoDocumento: String,
            numeroDocumento: String,
            contrasena: String
        )
    }

    interface Modelo {
        suspend fun registrarUsuario(context: Context,
                                     nombres: String,
                                     apellidos: String,
                                     direccion: String,
                                     correo: String,
                                     tipoDocumento: String,
                                     numeroDocumento: String,
                                     contrasena: String): Boolean
    }
}