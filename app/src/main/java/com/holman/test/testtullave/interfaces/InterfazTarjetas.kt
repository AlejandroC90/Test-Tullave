package com.holman.test.testtullave.interfaces

import android.content.Context
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.pruebainfotullave.data.TarjetaDetalle
import com.holman.test.testtullave.data.api.VolleyCallBack
import com.holman.test.testtullave.data.api.VolleyCallBackTarjeta
/**
 * Interfaz usada para las clases que permiten la ejecucion del la interfaz del mostrado de listado de tarjetas
 */
interface InterfazTarjetas {

    interface Vista {
        fun mostrarCargando()
        fun ocultarCargando()
        fun mostrarTarjetas(tarjetas: List<Tarjeta>)
        fun zeroTarjetas()

        fun tarjetaCreada(resultado: Boolean)

        fun tarjetaNoValida()
        fun tarjetaBorrada(resultado: Boolean)

        fun tarjetaConsultada(resultado: Boolean, tarjetaDetalle: TarjetaDetalle)


    }

    interface Presentador {
        fun agregarTarjeta(context: Context, numero: String, documentoUsuario: String)
        fun verInformacionTarjeta(context: Context, numero: String)
        fun listarTarjetas(context: Context, documentoUsuario: String)
        fun eliminarTarjeta(context: Context, numero: String)
    }

    interface Modelo {
        suspend fun agregarTarjeta(context: Context, numero: String, documentoUsuario: String, volleyCallBack: VolleyCallBack)
        suspend fun verInformacionTarjeta(context: Context, numero: String, volleyCallBackTarjeta: VolleyCallBackTarjeta)

        suspend fun listarTarjetas(context: Context, documentoUsuario: String): List<Tarjeta>
        fun eliminarTarjeta(context: Context, numero: String): Boolean
    }

}