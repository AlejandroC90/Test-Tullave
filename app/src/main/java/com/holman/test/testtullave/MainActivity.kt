package com.holman.test.testtullave

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.holman.test.testtullave.interfaces.InterfazInicioSesion
import com.holman.test.testtullave.presenters.InicioSesionPresenter

class MainActivity : AppCompatActivity(), InterfazInicioSesion.Vista {

    var presentador: InicioSesionPresenter? = null
    lateinit var dialog: AlertDialog
    lateinit var botonInicioSesion: Button
    lateinit var botonRegistro: Button
    lateinit var numeroDocumento: TextInputEditText
    lateinit var numeroDocumentoLayout: TextInputLayout
    lateinit var contrasena: TextInputEditText
    lateinit var contrasenaLayout: TextInputLayout

    lateinit var spinner: TextInputLayout





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inicial)
        inicializar()

        presentador = InicioSesionPresenter(this, this)
    }

    fun inicializar() {

        numeroDocumento = findViewById(R.id.editNumeroDocumento)
        contrasena = findViewById(R.id.editContrasena)


        botonInicioSesion = findViewById(R.id.botonInicioSesion)
        botonInicioSesion.setOnClickListener {
            if(validarFormulario()){
                presentador?.iniciarSesion(this,numeroDocumento.text.toString(),contrasena.text.toString())
            }
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

        spinner = findViewById(R.id.spinnerTipoDocumento)

        val documentos = resources.getStringArray(R.array.options_documentos)
        val adapter = ArrayAdapter(this, R.layout.list_item, documentos)
        (spinner.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        contrasenaLayout = findViewById(R.id.layouteditcontrasena)
        numeroDocumentoLayout = findViewById(R.id.layouteditnumerodocumento)
    }

    fun validarFormulario(): Boolean{
        if(contrasenaLayout.editText?.text.toString().isEmpty()){
            contrasenaLayout.error = getString(R.string.no_vacio)
            return false
        }else{
            contrasenaLayout.error = ""
        }
        if(numeroDocumentoLayout.editText?.text.toString().isEmpty()){
            contrasenaLayout.error = getString(R.string.no_vacio)
            return false
        }else{
            numeroDocumentoLayout.error = ""
        }
    return true
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