package com.holman.test.testtullave.data

import android.content.Context
import androidx.room.Room
import com.android.volley.toolbox.JsonArrayRequest
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.testtullave.data.api.VolleySingleton
import com.holman.test.testtullave.data.room.BaseDatosAplicacion
import com.holman.test.testtullave.data.room.RoomTarjeta
import com.holman.test.testtullave.data.room.TarjetaDao

class RepositorioTarjetas {

    /**
     * Funcion que agrega una tarjeta al registro de un usuario
     */
    fun agregarTarjeta(context: Context, numero: String): Boolean {
        var bd = interfazDBParaTarjetas(context)

        return try {
            bd.agregarTarjeta(RoomTarjeta(numero = numero))
            true
        }catch (e: Exception){
            false
        }
    }

    fun eliminarTarjeta(context: Context, numero: String): Boolean {
        var bd = interfazDBParaTarjetas(context)

        return try {
            bd.borrarTarjeta(numero)
            true
        }catch (e: Exception){
            false
        }
    }


    fun listarTarjetas(context: Context,): List<Tarjeta> {
        //var tarjetas: List<Tarjeta> = listOf(Tarjeta(number = "121212", estado = false, saldo = "10000"))

        var tarjetas: MutableList<Tarjeta> = mutableListOf<Tarjeta>()
        var bd = interfazDBParaTarjetas(context)

        var tarjetasRoom : List<RoomTarjeta> = bd.listarTarjetas()

        tarjetasRoom.forEach{
            tarjetas.add(Tarjeta(it.numero,false,""))
        }
        return tarjetas
    }

    /**
     * Acceso a base de datos de tarjetas
     */
    fun interfazDBParaTarjetas(context: Context): TarjetaDao {
        val db = Room.databaseBuilder(
            context,
            BaseDatosAplicacion::class.java, "usuarios"
        ).allowMainThreadQueries().build()

        return db.tarjetaDao()
    }


    /*fun listarPostsPorUsuario(
        context: Context,
        callback: CallBackVolleyPostsPorUsuario,
        usuario: DTOUsuario
    ) {
        val request = JsonArrayRequest(
            Request.Method.GET, URL_POST_USUARIOS + usuario.id.toString(), null,
            { response ->
                val arregloPosts = ArrayList<DTOPost>()
                for (i in 0 until response.length()) {
                    val post = DTOPost()
                    val objeto = response.getJSONObject(i)

                    post.titulo = objeto.getString("title")
                    post.cuerpo = objeto.getString("body")
                    arregloPosts.add(post)
                }
                callback.onSuccessPost(arregloPosts)
            },
            { error ->
                callback.onError()
            })
        addtoLista(request, context)
    }*/

    fun addtoLista(stringRequest: JsonArrayRequest, context: Context) {
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)

    }
}