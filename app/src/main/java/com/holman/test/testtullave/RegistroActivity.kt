package com.holman.test.testtullave

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import com.holman.test.testtullave.presenters.RegistroUsuarioPresenter

class RegistroActivity : AppCompatActivity(), InterfazRegistroUsuario.Vista {

    lateinit var presentador: RegistroUsuarioPresenter
    lateinit var botonRegistro: Button
    lateinit var numeroDocumento: EditText
    lateinit var contrasena: EditText
    lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registro)
        presentador = RegistroUsuarioPresenter(this, this)
        inicializarInterfaz()
    }

    fun inicializarInterfaz() {
        botonRegistro = findViewById(R.id.botonRegistroRegistro)
        contrasena = findViewById(R.id.editContrasenaRegistro)
        numeroDocumento = findViewById(R.id.editNumeroDocumentoRegistro)

        botonRegistro.setOnClickListener {
            presentador.registrarUsuario(
                this,
                "",
                "",
                "",
                "",
                "",
                numeroDocumento.text.toString(),
                contrasena.text.toString()
            )
        }

        //inicializacion de Alert Dialog para mostrar el cargando o no
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.dialog_cargando)
        dialog = builder.create()
    }

    override fun mostrarCargando() {
        dialog.show()

    }

    override fun ocultarCargando() {
        if (dialog.isShowing) {
            dialog.hide()
        }
    }

    override fun resultadoRegistro(resultado: Boolean) {
        if (resultado) {
            mostrarSnackBar(resources.getString(R.string.registro_exitoso))
            finish()
        } else {
            mostrarSnackBar(resources.getString(R.string.registro_error))
        }
    }

    fun mostrarSnackBar(mensaje: String) {
        Snackbar.make(findViewById(R.id.constrain_registro), mensaje, Snackbar.LENGTH_LONG).show()
    }
}