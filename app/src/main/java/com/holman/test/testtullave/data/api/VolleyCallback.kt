package com.holman.test.testtullave.data.api

import com.holman.test.pruebainfotullave.data.TarjetaDetalle

interface VolleyCallBack {
    fun onResultado(int: Int)
}

interface VolleyCallBackTarjeta {
    fun onResultado(resultado:Int, tarjeta: TarjetaDetalle)
}