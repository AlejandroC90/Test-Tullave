package com.holman.test.testtullave

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.holman.test.testtullave.interfaces.InterfazRegistroUsuario
import com.holman.test.testtullave.presenters.RegistroUsuarioPresenter

class RegistroActivity : AppCompatActivity(), InterfazRegistroUsuario.Vista {

    lateinit var presentador: RegistroUsuarioPresenter
    lateinit var botonRegistro: Button
    lateinit var dialog: AlertDialog

    lateinit var nombres: TextInputEditText
    lateinit var nombresLayout: TextInputLayout
    lateinit var apellidos: TextInputEditText
    lateinit var apellidosLayout: TextInputLayout
    lateinit var direccion: TextInputEditText
    lateinit var direccionLayout: TextInputLayout
    lateinit var correo: TextInputEditText
    lateinit var correoLayout: TextInputLayout
    lateinit var tipoDocumento: TextInputEditText
    lateinit var numeroDocumento: TextInputEditText
    lateinit var numeroDocumentoLayout: TextInputLayout
    lateinit var contrasena: TextInputEditText
    lateinit var contrasenaLayout: TextInputLayout

    lateinit var spinner: TextInputLayout
    lateinit var autoCompleteTextView: AutoCompleteTextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registro)
        presentador = RegistroUsuarioPresenter(this)
        inicializarInterfaz()
    }

    fun inicializarInterfaz() {
        botonRegistro = findViewById(R.id.botonRegistroRegistro)

        nombres = findViewById(R.id.editNombresRegistro)
        apellidos = findViewById(R.id.editApellidoRegistro)
        direccion = findViewById(R.id.editDireccionRegistro)
        correo = findViewById(R.id.editCorreoRegistro)
        //tipoDocumento
        contrasena = findViewById(R.id.editContrasenaRegistro)
        numeroDocumento = findViewById(R.id.editNumeroDocumentoRegistro)

        autoCompleteTextView = findViewById(R.id.autoCompleteRegistro)

        nombresLayout = findViewById(R.id.inputLayoutNombresRegistro)
        apellidosLayout = findViewById(R.id.inputLayoutApellidosRegistro)
        correoLayout = findViewById(R.id.inputLayoutCorreoRegistro)
        numeroDocumentoLayout = findViewById(R.id.inputLayoutNumeroDocumentoRegistro)
        contrasenaLayout = findViewById(R.id.layouteditcontrasenaRegistro)

        botonRegistro.setOnClickListener {
            if(validarFormulario()){
                presentador.registrarUsuario(
                    this,
                    nombres.text.toString(),
                    apellidos.text.toString(),
                    direccion.text.toString(),
                    correo.text.toString(),
                    autoCompleteTextView.text.toString(),
                    numeroDocumento.text.toString(),
                    contrasena.text.toString()
                )
            }
        }

        //inicializacion de Alert Dialog para mostrar el cargando o no
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.dialog_cargando)
        dialog = builder.create()

        spinner = findViewById(R.id.spinnerTipoDocumentoRegistro)

        val documentos = resources.getStringArray(R.array.options_documentos)
        val adapter = ArrayAdapter(this, R.layout.list_item, documentos)
        (spinner.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    /**
    * Funcion que permite validar el formulario de los datos de registro
    */
    fun validarFormulario(): Boolean {
        if (nombresLayout.editText?.text.toString().isEmpty()) {
            nombresLayout.error = getString(R.string.no_vacio)
            return false
        } else {
            nombresLayout.error = ""
        }
        if (apellidosLayout.editText?.text.toString().isEmpty()) {
            apellidosLayout.error = getString(R.string.no_vacio)
            return false
        } else {
            apellidosLayout.error = ""
        }
        if (correoLayout.editText?.text.toString().isEmpty()) {
            correoLayout.error = getString(R.string.no_vacio)
            return false
        } else {
            correoLayout.error = ""
        }
        if (spinner.editText?.text.toString().isEmpty()) {
            spinner.error = getString(R.string.no_vacio)
            return false
        } else {
            spinner.error = ""
        }
        if (numeroDocumentoLayout.editText?.text.toString().isEmpty()) {
            numeroDocumentoLayout.error = getString(R.string.no_vacio)
            return false
        } else if (numeroDocumentoLayout.editText?.text.toString().length < 10) {
            numeroDocumentoLayout.error = getString(R.string.longitud_incorrecta)
            return false
        } else {
            numeroDocumentoLayout.error = ""
        }
        if (contrasenaLayout.editText?.text.toString().isEmpty()) {
            contrasenaLayout.error = getString(R.string.no_vacio)
            return false
        } else {
            contrasenaLayout.error = ""
        }
        return true
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
        } else {
            mostrarSnackBar(resources.getString(R.string.registro_error))
        }
    }

    fun mostrarSnackBar(mensaje: String) {
        Snackbar.make(findViewById(R.id.constrain_registro), mensaje, Snackbar.LENGTH_LONG).show()
    }
}