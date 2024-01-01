package com.holman.test.testtullave.presenters

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import com.holman.test.testtullave.modelos.ModeloLogin
import com.holman.test.testtullave.modelos.ModeloRegistro
import kotlinx.coroutines.launch


class RegistroUsuarioPresenter(_vista: InterfazRegistroUsuario.Vista) :
    InterfazRegistroUsuario.Presentador, ViewModel() {
    private var vista = _vista
    private var model: ModeloRegistro = ModeloRegistro()
    override fun registrarUsuario(
        context: Context,
        nombres: String,
        apellidos: String,
        direccion: String,
        correo: String,
        tipoDocumento: String,
        numeroDocumento: String,
        contrasena: String
    ) {
        viewModelScope.launch {

            vista.mostrarCargando()
            val resultado = model.registrarUsuario(context,nombres,
                apellidos,
                direccion,
                correo,
                tipoDocumento,
                numeroDocumento,
                contrasena)
            vista.ocultarCargando()
            vista.resultadoRegistro(resultado)

        }
    }

}