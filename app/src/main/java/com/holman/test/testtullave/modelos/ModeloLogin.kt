package com.holman.test.testtullave.modelos

import android.content.Context
import com.holman.test.testtullave.data.Repositorio
import com.holman.test.testtullave.interfaces.InterfazAplicacion
import kotlinx.coroutines.delay

class ModeloLogin : InterfazAplicacion.Modelo {
    var repositorio: Repositorio = Repositorio()

    override suspend fun iniciarSesion(context: Context): Boolean {
        delay(2000)
        return repositorio.iniciarSesion("a", "b")
    }
}