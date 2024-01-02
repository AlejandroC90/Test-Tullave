package com.holman.test.testtullave

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.pruebainfotullave.data.TarjetaDetalle
import com.holman.test.testtullave.adapters.AdapterTarjetas
import com.holman.test.testtullave.interfaces.InterfazTarjetas
import com.holman.test.testtullave.presenters.TarjetasPresenter
import java.io.Serializable

class TarjetasActivity : AppCompatActivity(), InterfazTarjetas.Vista {

    lateinit var presentador: TarjetasPresenter
    lateinit var loadingDialog: AlertDialog
    lateinit var recyclearViewUsuarios: RecyclerView
    lateinit var notarjetas: TextView
    private lateinit var docUsuario: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tarjetas)
        revisarExtras()

        inicializar()
        presentador = TarjetasPresenter(this, this, docUsuario)
        setSupportActionBar(findViewById(R.id.toolbar_tarjetas))
    }

    fun inicializar() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.dialog_cargando)
        loadingDialog = builder.create()

        notarjetas = findViewById(R.id.text_no_tarjetas)

        //inicializacion de la lista de objetos - recycler view
        recyclearViewUsuarios = findViewById(R.id.listado_tarjetas_usuario)
        recyclearViewUsuarios.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_card -> {
                mostrarDialogoAgregarTarjeta()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun mostrarCargando() {
        loadingDialog.show()
    }

    override fun ocultarCargando() {
        if (loadingDialog.isShowing) {
            loadingDialog.hide()
        }
    }

    override fun mostrarTarjetas(tarjetas: List<Tarjeta>) {

        if (recyclearViewUsuarios.visibility.equals(View.INVISIBLE)) {
            recyclearViewUsuarios.visibility = View.VISIBLE
            notarjetas.visibility = View.INVISIBLE
        }
        recyclearViewUsuarios.adapter = AdapterTarjetas(tarjetas, itemClicked = { it: Tarjeta ->
            it.numero?.let { it1 ->
                mostrarDialogoConfirmacion(
                    "¿Esta seguro/a que desea borrar la tarjeta ${it.numero}?",
                    it1
                )
            }
        }, itemClickedInformacion = { it: Tarjeta ->
            it.numero?.let { it1 -> presentador.verInformacionTarjeta(this, it.numero) }
        })
    }

    override fun zeroTarjetas() {
        recyclearViewUsuarios.visibility = View.INVISIBLE
        notarjetas.visibility = View.VISIBLE
    }

    override fun tarjetaCreada(resultado: Boolean) {
        if (resultado) {
            mostrarSnackBar("Tarjeta creada con exito", true)
            presentador.listarTarjetas(this, docUsuario)
        } else {
            mostrarSnackBar("Ocurrio un error al crear la tarjeta", false)
        }
    }

    override fun tarjetaNoValida() {
        mostrarSnackBar(getString(R.string.tarjeta_no_valida), false)
    }

    override fun tarjetaBorrada(resultado: Boolean) {
        if (resultado) {
            mostrarSnackBar("Tarjeta borrada con exito", true)
            presentador.listarTarjetas(this, docUsuario)
        } else {
            mostrarSnackBar("Ha ocurrido un error al borrar la tarjeta", false)
        }

    }

    override fun tarjetaConsultada(resultado: Boolean, tarjetaDetalle: TarjetaDetalle) {
        if(resultado){
            val bundle:Bundle= Bundle().apply {
                //putString("user", "")
                putSerializable("tarjeta", tarjetaDetalle as Serializable)
            }
            startActivity(Intent(this,InformacionTarjeta::class.java).putExtras(bundle))
        }else{
            mostrarSnackBar(resources.getString(R.string.error_consulta_tarjeta), false)
        }
    }

    fun mostrarSnackBar(mensaje: String, exito: Boolean) {
        if(exito){
            Snackbar.make(findViewById(R.id.constrain_tarjetas), mensaje, Snackbar.LENGTH_LONG).setBackgroundTint(getColor(R.color.md_theme_light_primary)).show()
        }else{
            Snackbar.make(findViewById(R.id.constrain_tarjetas), mensaje, Snackbar.LENGTH_LONG).setBackgroundTint(getColor(R.color.md_theme_light_error)).show()
        }
    }

    /**
     * Funcion que muestra el dialogo de agregar tarjeta para que el usuario pueda digitar una tarjeta
     */
    private fun mostrarDialogoAgregarTarjeta() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogo_agregar)

        val editTextNumber: TextInputEditText = dialog.findViewById(R.id.editTextNumeroTarjeta)
        val btnCancel: Button = dialog.findViewById(R.id.btnCancel)
        val btnAccept: Button = dialog.findViewById(R.id.btnAccept)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnAccept.setOnClickListener {
            val enteredNumber = editTextNumber.text.toString()

            if (enteredNumber.length == 16) {
                presentador.agregarTarjeta(this, enteredNumber, docUsuario)
                dialog.dismiss()
            } else {
                // Show an error or prompt the user to enter a valid number
                editTextNumber.error = resources.getString(R.string.error_numero_tarjeta)
            }
        }

        dialog.show()
    }

    /**
     * Funciona que muestra el dialogo de confirmacion de borrado al momento de
     * eliminar una tarjeta
     */
    private fun mostrarDialogoConfirmacion(message: String, numero: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_confirmacion_borrado)

        val confirmationMessage: TextView = dialog.findViewById(R.id.confirmationMessage)
        val btnCancel: Button = dialog.findViewById(R.id.btnCancel)
        val btnConfirm: Button = dialog.findViewById(R.id.btnConfirm)

        confirmationMessage.text = message

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            presentador.eliminarTarjeta(this, numero)
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * Funcion que trae el documento del usuario loggeado enviado desde la pantalla principal
     */
    private fun revisarExtras() {
        intent?.extras?.let {
            if (it.containsKey("documento")) {
                docUsuario = it.getString("documento").toString()
            }
        }
    }
}