package com.holman.test.testtullave

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.presenters.InicioSesionPresenter

class MainActivity : AppCompatActivity(), InterfazInicioSesion.Vista {

    var presentador: InicioSesionPresenter? = null
    lateinit var dialog: AlertDialog
    lateinit var botonInicioSesion: Button
    lateinit var botonRegistro: Button
    lateinit var numeroDocumento: EditText
    lateinit var contrasena: EditText





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inicial)
        inicializar()

        presentador = InicioSesionPresenter(this, this)
    }

    fun inicializar() {

        numeroDocumento = findViewById(R.id.editNumeroDocumento)


        botonInicioSesion = findViewById(R.id.botonInicioSesion)
        botonInicioSesion.setOnClickListener {
            presentador?.iniciarSesion(this,numeroDocumento.text.toString(),"b")
        }

        botonRegistro = findViewById(R.id.botonRegistro)
        botonRegistro.setOnClickListener{
            startActivity(Intent(this,RegistroActivity::class.java))
        }

        //inicializacion de Alert Dialog para mostrar el cargando o no
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.dialog_cargando)
        dialog = builder.create()



    }

    fun mostrarSnackBar(mensaje : String){
        Snackbar.make(findViewById(R.id.constrain_main), mensaje, Snackbar.LENGTH_LONG).show()
    }

    override fun mostrarCargando() {
        dialog.show()
    }

    override fun ocultarCargando() {
        if(dialog.isShowing){
            dialog.hide()
        }
    }


    override fun resultadoInicioSesion(resultado: Boolean) {
        if(resultado){
            startActivity(Intent(this,TarjetasActivity::class.java))
        }else{
            mostrarSnackBar(resources.getString(R.string.error_inicio_sesion))
        }
    }
}