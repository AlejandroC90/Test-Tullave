package com.holman.test.testtullave.interfaces

import android.content.Context
import com.holman.test.pruebainfotullave.data.Tarjeta

interface InterfazTarjetas {

    interface Vista {
        fun mostrarCargando()
        fun ocultarCargando()
        fun mostrarTarjetas(tarjetas: List<Tarjeta>)
        fun zeroTarjetas()

        fun tarjetaCreada(resultado: Boolean)
        fun tarjetaBorrada(resultado: Boolean)


    }

    interface Presentador {
        fun agregarTarjeta(context: Context, numero: String)
        fun listarTarjetas(context: Context)
        fun eliminarTarjeta(context: Context, numero: String)
    }

    interface Modelo {
        fun agregarTarjeta(context: Context, numero: String): Boolean
        suspend fun listarTarjetas(context: Context): List<Tarjeta>
        fun eliminarTarjeta(context: Context, numero: String): Boolean
    }

}