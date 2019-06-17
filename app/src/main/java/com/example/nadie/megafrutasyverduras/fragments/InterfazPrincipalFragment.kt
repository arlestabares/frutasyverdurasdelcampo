package com.example.nadie.megafrutasyverduras.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.actividades.*
import com.example.nadie.megafrutasyverduras.modelo.Producto
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.fragment_interfaz_principal.*
import kotlinx.android.synthetic.main.fragment_interfaz_principal.view.*
import org.jetbrains.anko.support.v4.toast

class InterfazPrincipalFragment : Fragment(), View.OnClickListener {

    lateinit var vista: View
    var listaProductos: ArrayList<Producto>? = null
    var listaCompra: ArrayList<Registro>? = null
    var listaStock: ArrayList<Registro>? = null
    var listaParaDonacion: ArrayList<Registro>? = null
    var listaProveedores: ArrayList<Proveedor>? = null
    lateinit var listener: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_interfaz_principal, container, false)

        /*vista.cardViewRC.setOnClickListener(this)
        vista.cardViewLRC.setOnClickListener(this)
        vista.cardViewRStock.setOnClickListener(this)
        vista.cardViewLRStock.setOnClickListener(this)
        vista.cardViewRD.setOnClickListener(this)
        vista.cardViewLD.setOnClickListener(this)
        vista.cardViewRP.setOnClickListener(this)
        vista.cardViewLP.setOnClickListener(this)
        vista.cardViewIA.setOnClickListener(this)

        listaProductos = arguments?.getParcelableArrayList("listaProductos")
        listaCompra = arguments?.getParcelableArrayList("listaCompra")
        listaStock = arguments?.getParcelableArrayList("listaStock")
        listaProveedores = arguments?.getParcelableArrayList("listaProveedores")
        listaParaDonacion = arguments?.getParcelableArrayList("listaDonacionFV")

        Log.e("entroInterfazPrincipal", listaParaDonacion.toString())*/

        return vista
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onClick(v: View?) {



    }



}
