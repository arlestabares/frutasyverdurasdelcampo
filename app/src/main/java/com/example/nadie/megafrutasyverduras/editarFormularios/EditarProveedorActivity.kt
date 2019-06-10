package com.example.nadie.megafrutasyverduras.editarFormularios

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.adapter.AdapterProveedores
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import kotlinx.android.synthetic.main.activity_editar_proveedor.*

class EditarProveedorActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    var posicion: Int = 0
    lateinit var registroProveedor: Proveedor
    lateinit var adapter: AdapterProveedores
    lateinit var adapterSpinnerCiudad:ArrayAdapter<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_proveedor)

        btnEditarProveedorEP.setOnClickListener(this)

        //intent proveniente desde AdapterProveedores
        registroProveedor = intent.getParcelableExtra("listaDesdeProveedorAdapter")
        posicion=intent.getIntExtra("posicion",0)

        ediTxtNombreEP.setText( registroProveedor.nombre)
        ediTxtTelefonoEP.setText( registroProveedor.telefono)
        ediTxtFechaRegistroEP.setText( registroProveedor.fechaRegistro)

        adapterSpinnerCiudad =  ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
        spinnerCiudadEP.adapter=adapterSpinnerCiudad

        var posCiudad = adapterSpinnerCiudad.getPosition(registroProveedor.ciudad)
        spinnerCiudadEP.setSelection(posCiudad)
    }


    override fun onClick(v: View?) {

        if (v?.id == btnEditarProveedorEP.id) {

            registroProveedor.nombre = ediTxtNombreEP.text.toString()
            registroProveedor.ciudad = spinnerCiudadEP.selectedItem.toString()
            registroProveedor.telefono = ediTxtTelefonoEP.text.toString()
            registroProveedor.fechaRegistro = ediTxtTelefonoEP.text.toString()

            val intent = Intent()
            intent.putExtra("registro", registroProveedor)
            intent.putExtra("posicion", posicion)
            setResult(Activity.RESULT_OK,intent)

            finish()

        }
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}
