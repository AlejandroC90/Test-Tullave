package com.holman.test.testtullave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.holman.test.pruebainfotullave.data.TarjetaDetalle

class InformacionTarjeta : AppCompatActivity() {
    private var tarjetaDetalle = TarjetaDetalle()

    lateinit var nombreText: TextView
    lateinit var numeroText: TextView
    lateinit var bancoText: TextView
    lateinit var perfilText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_tarjeta)
        revisarExtras()
        inicializarInterfaz()
    }

    private fun inicializarInterfaz(){
        nombreText = findViewById(R.id.nombre_tarjeta_detalle)
        numeroText = findViewById(R.id.numero_tarjeta_detalle)
        bancoText = findViewById(R.id.banco_tarjeta_detalle)
        perfilText = findViewById(R.id.perfil_tarjeta_detalle)


        setSupportActionBar(findViewById(R.id.my_toolbar_informacion))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        nombreText.text = tarjetaDetalle.nombre
        numeroText.text = tarjetaDetalle.numero
        bancoText.text = tarjetaDetalle.banco
        perfilText.text = tarjetaDetalle.perfil
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun revisarExtras(){
        intent?.extras?.let {

            if(it.containsKey("tarjeta")){
                tarjetaDetalle = it.getSerializable("tarjeta") as TarjetaDetalle
            }
        }
    }
}