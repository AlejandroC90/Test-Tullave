package com.holman.test.testtullave.presenters

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holman.test.pruebainfotullave.data.TarjetaDetalle
import com.holman.test.testtullave.data.api.VolleyCallBack
import com.holman.test.testtullave.data.api.VolleyCallBackTarjeta
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import com.holman.test.testtullave.interfaces.InterfazTarjetas
import com.holman.test.testtullave.modelos.ModeloLogin
import com.holman.test.testtullave.modelos.ModeloRegistro
import com.holman.test.testtullave.modelos.ModeloTarjetas
import kotlinx.coroutines.launch


class TarjetasPresenter(
    _vista: InterfazTarjetas.Vista,
    context: Context,
    documentoUsuario: String
) :
    InterfazTarjetas.Presentador, ViewModel() {
    private var vista = _vista
    private var model: ModeloTarjetas = ModeloTarjetas()

    init {
        listarTarjetas(context, documentoUsuario)
    }

    override fun agregarTarjeta(context: Context, numero: String, documentoUsuario: String) {
        viewModelScope.launch {
            vista.mostrarCargando()
            model.agregarTarjeta(context, numero, documentoUsuario, object : VolleyCallBack {
                override fun onResultado(int: Int) {
                    if (int == 1) {
                        vista.tarjetaCreada(true)
                    } else if (int == 0) {
                        vista.tarjetaNoValida()
                    } else {
                        vista.tarjetaCreada(false)
                    }
                }
            }
            )
            vista.ocultarCargando()
        }
    }

    override fun verInformacionTarjeta(context: Context, numero: String) {
        viewModelScope.launch {
            vista.mostrarCargando()
            model.verInformacionTarjeta(context, numero, object : VolleyCallBackTarjeta {
                override fun onResultado(int: Int, tarjeta: TarjetaDetalle) {
                    if (int == 1) {
                        vista.tarjetaConsultada(true, tarjeta)
                    } else {
                        vista.tarjetaConsultada(false, tarjeta)
                    }
                }
            }
            )
            vista.ocultarCargando()
        }
    }

    override fun listarTarjetas(context: Context, documentoUsuario: String) {
        viewModelScope.launch {

            vista.mostrarCargando()
            val tarjetas = model.listarTarjetas(context, documentoUsuario)
            vista.ocultarCargando()
            if (tarjetas.isEmpty()) {
                vista.zeroTarjetas()
            } else {
                vista.mostrarTarjetas(tarjetas)

            }

        }
    }

    override fun eliminarTarjeta(context: Context, numero: String) {
        viewModelScope.launch {

            vista.mostrarCargando()
            val tarjetas = model.eliminarTarjeta(context, numero)
            vista.ocultarCargando()
            vista.tarjetaBorrada(tarjetas)

        }
    }

}