package com.holman.test.testtullave.modelos

import android.content.Context
import com.holman.test.testtullave.data.RepositorioUsuarios
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import kotlinx.coroutines.delay

class ModeloRegistro : InterfazRegistroUsuario.Modelo {
    var repositorio: RepositorioUsuarios = RepositorioUsuarios()

    override suspend fun registrarUsuario(
        context: Context,
        nombres: String,
        apellidos: String,
        direccion: String,
        correo: String,
        tipoDocumento: String,
        numeroDocumento: String,
        contrasena: String
    ): Boolean {
        delay(2000)
        return repositorio.registrarUsuario( context, nombres,
            apellidos,
            direccion,
            correo,
            tipoDocumento,
            numeroDocumento,
            contrasena)
    }
}