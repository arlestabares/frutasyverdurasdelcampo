package com.example.nadie.megafrutasyverduras.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Producto

class AdapterProductos(var listaProductos: ArrayList<Producto>) : RecyclerView.Adapter<AdapterProductos.ViewHolderProducto>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderProducto {
        //En la variable v, guardamos la vista que vamos a inflar y a retornar

        val vista = LayoutInflater.from(p0.context).inflate(R.layout.molde_carview_interfaz_principal, p0, false)
        return ViewHolderProducto(vista)
    }

    override fun getItemCount(): Int {
        return listaProductos.size

    }

    override fun onBindViewHolder(holder:ViewHolderProducto, posicion: Int) {

        holder.bindItems(listaProductos[posicion])

    }

    /**
     * Clase ViewHolder anidada
     * Esta clase maneja la vista.
     * El View que agregaremos al Metodo ViewHolder(view) es el mismo que espera la clase ViewHolderProducto
     */
    class ViewHolderProducto(view: View) : RecyclerView.ViewHolder(view) {

        /**
         *Este metodo bindItems recibe los datos que se agregaran dentro de la vista
         *Creamos variables para las vistas
         * @image
         * @nombre
         *
         */
        fun bindItems(data: Producto) {

            val image: ImageView = itemView.findViewById(R.id.imageManzanaCarview)
            // val titulo:TextView=itemView.findViewById(R.id.txtViewTituloCarview)
            val titulo: TextView = itemView.findViewById(R.id.txtViewDescripcionCarview)

            Glide.with(itemView.context).load(data.imagen).into(image)
            titulo.text = data.titulo
        }

    }
}
