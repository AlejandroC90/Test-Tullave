package com.holman.test.testtullave.modelos

import android.content.Context
import com.holman.test.testtullave.data.RepositorioUsuarios
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import kotlinx.coroutines.delay

class ModeloLogin : InterfazInicioSesion.Modelo {
    var repositorio: RepositorioUsuarios = RepositorioUsuarios()

    override suspend fun iniciarSesion(context: Context, documento: String, contrasena: String): Boolean {
        delay(2000)
        return repositorio.iniciarSesion(context,documento, contrasena)
    }
}