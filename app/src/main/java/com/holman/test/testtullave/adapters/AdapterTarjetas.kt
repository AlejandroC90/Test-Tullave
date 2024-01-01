package com.holman.test.testtullave.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.holman.test.pruebainfotullave.data.Tarjeta
import com.holman.test.testtullave.R

/**
 * Adapter para mostrar el listado de usuarios despues de cargar
 * usando un Recycler View
 */
class AdapterTarjetas(
    private val listadoUsuarios: List<Tarjeta>,
    private var itemClicked: ((tarjeta: Tarjeta) -> Unit),
) :
    RecyclerView.Adapter<AdapterTarjetas.ViewHolder>() {


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.nombre_tarjeta)
        val txtTelefono: TextView = itemView.findViewById(R.id.numero_tarjeta)
        val bottonPublicacion: Button = itemView.findViewById(R.id.boton_eliminar_tarjeta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tarjeta_usuario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val u = listadoUsuarios[position]
        holder.txtNombre.text = u.number
        holder.txtTelefono.text = u.saldo

        holder.bottonPublicacion.setOnClickListener {
            itemClicked(u)
        }
    }

    override fun getItemCount(): Int {
        return listadoUsuarios.size
    }
}