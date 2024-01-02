package com.holman.test.testtullave.data

import android.content.Context
import androidx.room.Room
import com.android.volley.toolbox.JsonObjectRequest
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.pruebainfotullave.data.TarjetaDetalle
import com.holman.test.testtullave.data.api.VolleyCallBack
import com.holman.test.testtullave.data.api.VolleyCallBackTarjeta
import com.holman.test.testtullave.data.api.VolleySingleton
import com.holman.test.testtullave.data.room.BaseDatosAplicacion
import com.holman.test.testtullave.data.room.RoomTarjeta
import com.holman.test.testtullave.data.room.TarjetaDao
import org.json.JSONObject

class RepositorioTarjetas {

    final val URL_BASE = "https://osgqhdx2wf.execute-api.us-west-2.amazonaws.com/"
    final val URL_VALIDAR_TARJETA = URL_BASE.plus("card/valid/")
    final val URL_BUSCAR_DATOS_TARJETA = URL_BASE.plus("card/getInformation/")

    /**
     * Funcion que agrega una tarjeta al registro de un usuario, primero revisa que no esté registrada ya
     * y luego la valida con el servicio antes de agregarla
     */
    fun agregarTarjeta(context: Context, numero: String, documentoUsuario: String, volleyCallBack: VolleyCallBack) {
        var resultadoValidacion = false
        //primero validamos que la tarjeta ya no este en base de datos
        var bd = interfazDBParaTarjetas(context)
        var busqueda = bd.buscarTarjeta(numero, documentoUsuario)
        if(busqueda.isNotEmpty()){
            volleyCallBack.onResultado(2)
            return
        }

        val request = object : JsonObjectRequest(
            Method.GET,
            URL_VALIDAR_TARJETA + numero,
            null,
            { response ->
                val jsonObject = response as JSONObject

                resultadoValidacion = jsonObject.getBoolean("isValid")

                //si la validacion de la tarjeta es correcta entonces se continua con el guardado
                if(resultadoValidacion){
                    try {
                        bd.agregarTarjeta(RoomTarjeta(numero = numero, usuario = documentoUsuario))
                        volleyCallBack.onResultado(1)
                    } catch (e: Exception) {
                        volleyCallBack.onResultado(2)
                    }
                }else{
                    volleyCallBack.onResultado(0)//no valida
                }
            },
            { error ->
                error.printStackTrace()
                volleyCallBack.onResultado(2)
            })
            {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                // Replace YOUR_BEARER_TOKEN with your actual Bearer token
                headers["Authorization"] = "Bearer ${Servicios().TOKEN}"
                return headers
            }

        }
        agregarAColaServicios(request, context)
    }

    /**
     * Funciona que busca la informacion de una tarjeta usando su numero
     */
    fun buscarInformacionTarjeta(context: Context, numero: String, volleyCallBackTarjeta: VolleyCallBackTarjeta) {
        var tarjeta = TarjetaDetalle()

        val request = object : JsonObjectRequest(
            Method.GET,
            URL_BUSCAR_DATOS_TARJETA + numero,
            null,
            { response ->
                val jsonObject = response as JSONObject
                /*
                                {
                  "cardNumber": "string",
                  "profileCode": "string",
                  "profile": "string",
                  "profile_es": "string",
                  "bankCode": "string",
                  "bankName": "string",
                  "userName": "string",
                  "userLastName": "string"
                }
                * */

                tarjeta.banco =  jsonObject.getString("bankName")
                tarjeta.nombre =  jsonObject.getString("userName") + " " + jsonObject.getString("userLastName")
                tarjeta.numero =  jsonObject.getString("cardNumber")
                tarjeta.perfil = jsonObject.getString("profile_es")
                volleyCallBackTarjeta.onResultado(1, tarjeta)
            },
            { error ->
                volleyCallBackTarjeta.onResultado(2, tarjeta)
            })
        {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                // Replace YOUR_BEARER_TOKEN with your actual Bearer token
                headers["Authorization"] = "Bearer ${Servicios().TOKEN}"
                return headers
            }
        }
        agregarAColaServicios(request, context)
    }

    fun eliminarTarjeta(context: Context, numero: String): Boolean {
        var bd = interfazDBParaTarjetas(context)

        return try {
            bd.borrarTarjeta(numero)
            true
        } catch (e: Exception) {
            false
        }
    }


    /**
     * Funcion que permite listar las tarjetas de un usuario en especifico
     */
    fun listarTarjetas(context: Context, documentoUsuario: String): List<Tarjeta> {
        //var tarjetas: List<Tarjeta> = listOf(Tarjeta(number = "121212", estado = false, saldo = "10000"))

        var tarjetas: MutableList<Tarjeta> = mutableListOf<Tarjeta>()
        var bd = interfazDBParaTarjetas(context)

        var tarjetasRoom: List<RoomTarjeta> = bd.listarTarjetas(documentoUsuario)

        tarjetasRoom.forEach {
            tarjetas.add(Tarjeta(it.numero, ""))
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


    /**
     * Funcion que permite agregar una peticion a la cola de peticiones requeridas
     */
    fun agregarAColaServicios(request: JsonObjectRequest, context: Context) {
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }
}