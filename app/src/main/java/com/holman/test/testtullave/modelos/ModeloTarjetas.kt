package com.holman.test.testtullave.modelos

import android.content.Context
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.testtullave.data.RepositorioTarjetas
import com.holman.test.testtullave.data.RepositorioUsuarios
import com.holman.test.testtullave.data.api.VolleyCallBack
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import com.holman.test.testtullave.interfaces.InterfazTarjetas
import kotlinx.coroutines.delay

class ModeloTarjetas : InterfazTarjetas.Modelo {
    var repositorio: RepositorioTarjetas = RepositorioTarjetas()
    override suspend fun agregarTarjeta(context: Context, numero: String, documentoUsuario: String, volleyCallBack: VolleyCallBack) {
        return repositorio.agregarTarjeta(context, numero, documentoUsuario, volleyCallBack)
    }

    override suspend fun listarTarjetas(context: Context, documentoUsuario: String):List<Tarjeta> {
        delay(2000)
        return repositorio.listarTarjetas(context, documentoUsuario)
    }

    override fun eliminarTarjeta(context: Context, numero: String): Boolean {
        //delay(2000)
        return repositorio.eliminarTarjeta(context, numero)
    }


}