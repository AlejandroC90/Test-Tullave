package com.holman.test.testtullave.presenters

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.modelos.ModeloLogin
import kotlinx.coroutines.launch


class InicioSesionPresenter(_vista: InterfazInicioSesion.Vista, context: Context) :
    InterfazInicioSesion.Presentador, ViewModel() {
    private var vista = _vista
    private var model: ModeloLogin = ModeloLogin()
    override fun iniciarSesion(context: Context, documento: String, contrasena: String) {
        viewModelScope.launch {

            vista.mostrarCargando()
            val resultado = model.iniciarSesion(context, documento, contrasena)
            vista.ocultarCargando()
            vista.resultadoInicioSesion(resultado)

        }
    }

}