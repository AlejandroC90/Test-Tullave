package com.holman.test.testtullave.modelos

import android.content.Context
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.testtullave.data.RepositorioTarjetas
import com.holman.test.testtullave.data.RepositorioUsuarios
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import com.holman.test.testtullave.interfaces.InterfazTarjetas
import kotlinx.coroutines.delay

class ModeloTarjetas : InterfazTarjetas.Modelo {
    var repositorio: RepositorioTarjetas = RepositorioTarjetas()
    override fun agregarTarjeta(context: Context, numero: String): Boolean {
        return repositorio.agregarTarjeta(context, numero)
    }

    override suspend fun listarTarjetas(context: Context):List<Tarjeta> {
        delay(2000)
        return repositorio.listarTarjetas(context)
    }

    override fun eliminarTarjeta(context: Context, numero: String): Boolean {
        //delay(2000)
        return repositorio.eliminarTarjeta(context, numero)
    }


}