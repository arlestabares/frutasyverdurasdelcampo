package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import kotlinx.android.synthetic.main.activity_formulario_proveedor.*

class FormularioProveedorActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    lateinit var registroProveedor: Proveedor
    lateinit var spinnerCiudadProveedor: ArrayAdapter<CharSequence>
    lateinit var spinnerCiudad: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_proveedor)

        btnRegistrarFS.setOnClickListener(this)
        btnCancelarFS.setOnClickListener(this)
        spinnerCiudad = findViewById(R.id.spinnerCiudadP) as Spinner

        spinnerCiudad.onItemSelectedListener = this

        spinnerCiudadProveedor =
            ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
       spinnerCiudad.adapter=spinnerCiudadProveedor
    }


    override fun onClick(v: View?) {

        if (v?.id == btnCancelarFS.id) {
            finish()

        } else if (v?.id == btnRegistrarFS.id) {

            val nombre: String = ediTxtNombreP.text.toString()
            val telefono: String = ediTxtTelefonoP.text.toString()
            val ciudad: String = spinnerCiudadP.selectedItem.toString()
            val fechaRegistro: String = ediTxtFechaRegistroFS.text.toString()

            registroProveedor = Proveedor()

            registroProveedor.nombre = nombre
            registroProveedor.ciudad = ciudad
            registroProveedor.telefono = telefono
            registroProveedor.fechaRegistro = fechaRegistro

            //  listaProveedores.add(registroProveedor)
            var intent = Intent()
            intent.putExtra("registroProveedor", registroProveedor)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position==0){
           // spinnerCiudades.adapter=spinnerCiudadProveedor

        }
    }
}
