package com.holman.test.testtullave.interfaces

import android.content.Context
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.testtullave.data.api.VolleyCallBack

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
        fun agregarTarjeta(context: Context, numero: String, documentoUsuario: String)
        fun listarTarjetas(context: Context, documentoUsuario: String)
        fun eliminarTarjeta(context: Context, numero: String)
    }

    interface Modelo {
        suspend fun agregarTarjeta(context: Context, numero: String, documentoUsuario: String, volleyCallBack: VolleyCallBack)
        suspend fun listarTarjetas(context: Context, documentoUsuario: String): List<Tarjeta>
        fun eliminarTarjeta(context: Context, numero: String): Boolean
    }

}