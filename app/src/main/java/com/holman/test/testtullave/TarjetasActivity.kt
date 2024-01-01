package com.holman.test.testtullave

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
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
import com.holman.test.testtullave.adapters.AdapterTarjetas
import com.holman.test.testtullave.interfaces.InterfazTarjetas
import com.holman.test.testtullave.presenters.TarjetasPresenter

class TarjetasActivity : AppCompatActivity(), InterfazTarjetas.Vista {

    lateinit var presentador: TarjetasPresenter
    lateinit var dialog: AlertDialog
    lateinit var recyclearViewUsuarios: RecyclerView
    lateinit var notarjetas: TextView
    lateinit var botonAgregarTarjeta: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tarjetas)

        inicializar()
        presentador = TarjetasPresenter(this, this)
        setSupportActionBar(findViewById(R.id.toolbar_tarjetas))

    }

    fun inicializar() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.dialog_cargando)
        dialog = builder.create()

        notarjetas = findViewById(R.id.text_no_tarjetas)

        //inicializacion de la lista de objetos - recycler view
        recyclearViewUsuarios = findViewById(R.id.listado_tarjetas_usuario)
        recyclearViewUsuarios.layoutManager = LinearLayoutManager(this)

        botonAgregarTarjeta = findViewById(R.id.botonAgregarTarjeta)
        botonAgregarTarjeta.setOnClickListener {
            mostrarDialogoAgregarTarjeta()
        }
    }

    override fun mostrarCargando() {
        dialog.show()

    }

    override fun ocultarCargando() {
        if (dialog.isShowing) {
            dialog.hide()
        }
    }

    override fun mostrarTarjetas(tarjetas: List<Tarjeta>) {

        if(recyclearViewUsuarios.visibility.equals(View.INVISIBLE)){
            recyclearViewUsuarios.visibility = View.VISIBLE
            notarjetas.visibility = View.INVISIBLE
        }
        recyclearViewUsuarios.adapter = AdapterTarjetas(tarjetas) { it: Tarjeta ->
            it.number?.let { it1 ->
                mostrarDialogoConfirmacion("Esta seguro que desea borrar la tarjeta ${it.number}",
                    it1
                )
            }
        }
    }

    override fun zeroTarjetas() {
        recyclearViewUsuarios.visibility = View.INVISIBLE
        notarjetas.visibility = View.VISIBLE
    }

    override fun tarjetaCreada(resultado: Boolean) {
        if(resultado){
            mostrarSnackBar( "Tarjeta creada con exito")
            presentador.listarTarjetas(this)
        }else{
            mostrarSnackBar( "Tarjeta creada con error")
        }
    }

    override fun tarjetaBorrada(resultado: Boolean) {
        if(resultado){
            mostrarSnackBar( "Tarjeta borrada con exito")
            presentador.listarTarjetas(this)
        }else{
            mostrarSnackBar( "Tarjeta borrada con error")
        }

    }

    fun mostrarSnackBar(mensaje : String){
        Snackbar.make(findViewById(R.id.constrain_tarjetas), mensaje, Snackbar.LENGTH_LONG).show()
    }

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
                presentador.agregarTarjeta(this, enteredNumber)
                dialog.dismiss()
            } else {
                // Show an error or prompt the user to enter a valid number
                editTextNumber.error = resources.getString(R.string.error_numero_tarjeta)
            }
        }

        dialog.show()
    }

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
}