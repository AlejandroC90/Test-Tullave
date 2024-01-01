package com.holman.test.testtullave.presenters

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import com.holman.test.testtullave.interfaces.InterfazTarjetas
import com.holman.test.testtullave.modelos.ModeloLogin
import com.holman.test.testtullave.modelos.ModeloRegistro
import com.holman.test.testtullave.modelos.ModeloTarjetas
import kotlinx.coroutines.launch


class TarjetasPresenter(_vista: InterfazTarjetas.Vista, context: Context) :
    InterfazTarjetas.Presentador, ViewModel() {
    private var vista = _vista
    private var model: ModeloTarjetas = ModeloTarjetas()

    init {
        listarTarjetas(context)
    }

    override fun agregarTarjeta(context: Context, numero: String) {
        viewModelScope.launch {

            vista.mostrarCargando()
            val tarjetas = model.agregarTarjeta(context, numero)
            vista.ocultarCargando()
            vista.tarjetaCreada(tarjetas)

        }
    }

    override fun listarTarjetas(context: Context) {
        viewModelScope.launch {

            vista.mostrarCargando()
            val tarjetas = model.listarTarjetas(context)
            vista.ocultarCargando()
            if(tarjetas.isEmpty()){
                vista.zeroTarjetas()
            }else{
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