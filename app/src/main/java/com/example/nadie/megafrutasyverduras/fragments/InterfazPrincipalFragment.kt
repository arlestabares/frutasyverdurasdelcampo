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

class InterfazPrincipalFragment : Fragment(), View.OnClickListener {

    lateinit var vista: View
    var listaProductos: ArrayList<Producto>? = null
    var listaCompra: ArrayList<Registro>? = null
    var listaStock: ArrayList<Registro>? = null
    var listaDescomposicion: ArrayList<Registro>? = null
    var listaProveedores: ArrayList<Proveedor>? = null
    lateinit var listener: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_interfaz_principal, container, false)

        vista.cardViewRC.setOnClickListener(this)
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
        listaDescomposicion = arguments?.getParcelableArrayList("listaDescomposicionFV")


        return vista
    }


    override fun onClick(v: View?) {

        if (v == cardViewRC) {
            var intent = Intent(activity, FormularioCompraActivity::class.java)
            // intent.putParcelableArrayListExtra("registros", listaCompra)
            startActivityForResult(intent, 12)
        } else if (v == cardViewLRC) {
            var intent = Intent(activity, ListarCompraActivity::class.java)
            intent.putParcelableArrayListExtra("registros", listaCompra)
            startActivityForResult(intent, 13)
        } else if (v == cardViewRStock) {
            var intent = Intent(activity, FormularioStockActivity::class.java)
            // intent.putParcelableArrayListExtra("registros", listaStock)
            startActivityForResult(intent, 14)
        } else if (v == cardViewLRStock) {
            var intent = Intent(activity, ListarStockActivity::class.java)
            intent.putParcelableArrayListExtra("registros", listaStock)
            startActivityForResult(intent, 15)
        } else if (v == cardViewRD) {
            var intent = Intent(activity, FormularioDescomposicionActivity::class.java)
            // intent.putParcelableArrayListExtra("resgistros", listaDescomposicion)
            startActivityForResult(intent, 16)
        } else if (v == cardViewLD) {
            var intent = Intent(activity, ListarDescomposiconActivity::class.java)
            intent.putParcelableArrayListExtra("registros", listaDescomposicion)
            startActivityForResult(intent, 17)
        } else if (v == cardViewRP) {
            var intent = Intent(activity, FormularioProveedorActivity::class.java)
            // intent.putExtra("registros", listaProveedores)
            startActivityForResult(intent, 18)
        } else if (v == cardViewLP) {
            var intent = Intent(activity, ListarProveedoresActivity::class.java)
            intent.putParcelableArrayListExtra("registros", listaProveedores)
            startActivityForResult(intent, 19)
        } else if (v == cardViewIA) {
            var intent = Intent(activity, InformacionFuncionalidadActivity::class.java)
            //  intent.putExtra("registros", listaCompra)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                var listaCompra = data?.getParcelableExtra<Registro>("registroCompra")
                listener.registrarProducto(listaCompra!!, 12)
            }
        } else if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                var editarCompra = data?.getParcelableExtra<Registro>("registro")
                var pos = data?.getIntExtra("posicion", 0)
                listener.editarProducto(pos!!, editarCompra!!, 13)
            }

        } else if (requestCode == 14) {
            if (resultCode == Activity.RESULT_OK) {
                var registrarStock = data?.getParcelableExtra<Registro>("registroFormularioStock")
                listener.registrarProducto(registrarStock!!, 14)
                Log.e("registrostock", registrarStock.toString())
            }
        } else if (requestCode == 15) {
            if (resultCode == Activity.RESULT_OK) {
                var editarStock = data?.getParcelableExtra<Registro>("registroDesdeListarStock")
                var pos = data?.getIntExtra("posicion", 0)
                listener.editarProducto(pos!!, editarStock!!, 15)
            }

        } else if (requestCode == 16) {
            if (resultCode == Activity.RESULT_OK) {
                var registrarDescomposicion = data?.getParcelableExtra<Registro>("registroDescomposicion")
                listener.registrarProducto(registrarDescomposicion!!, 16)
                Log.e("registroDescomposicion", registrarDescomposicion.toString())
            }

        } else if (requestCode == 17) {
            if (resultCode == Activity.RESULT_OK) {
                val editarDescomposicion = data?.getParcelableExtra<Registro>("registroDesdeListarDescomposicion")
                val posicion = data?.getIntExtra("posicion", 0)
                listener.editarProducto(posicion!!, editarDescomposicion!!, 17)
            }
        } else if (requestCode == 18) {
            if (resultCode == Activity.RESULT_OK) {
                var listaProveedores = data?.getParcelableExtra<Proveedor>("registroProveedor")
                listener.registrarProveedor(listaProveedores!!)
            }
        } else if (requestCode == 19) {
            if (resultCode == Activity.RESULT_OK) {
                var editarListaProveedores = data?.getParcelableExtra<Proveedor>("")
                var pos = data?.getIntExtra("posicion", 0)
                listener.editarProveedor(pos!!, editarListaProveedores!!)
            }

        }

    }

}
