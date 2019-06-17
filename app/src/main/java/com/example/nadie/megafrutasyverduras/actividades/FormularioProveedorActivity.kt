package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Spinner
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import kotlinx.android.synthetic.main.activity_formulario_proveedor.*
import java.util.*

class FormularioProveedorActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    lateinit var registroProveedor: Proveedor
    lateinit var spinnerCiudadProveedor: ArrayAdapter<CharSequence>
    lateinit var spinnerCiudad: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_proveedor)



        cargarRegistros()
        mostrarCalendario()
    }


    /**
     * Funcion encargada de cargar los datos de la aplicacion y de las escuchas de
     * los botones para sus correspondientes acciones
     */

    fun cargarRegistros(){
        btnRegistrarFS.setOnClickListener(this)
        btnCancelarFS.setOnClickListener(this)
        btnFecha.setOnClickListener(this)

        spinnerCiudadP.onItemSelectedListener = this

        spinnerCiudadProveedor =
            ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
        spinnerCiudadP.adapter = spinnerCiudadProveedor
    }

    /**
     * Funcion encargada de mostrar el calendario cuando el usuario seleccione el boton de seleccionFecha
     */
    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnFecha.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                ediTxtFechaRegistroFS.setText("" + dayOfMonth + "/" + month + "/" + day)

            }, year, month, day)
            dpd.show()
        }

    }

    /**
     * Funcion encargada de escuchar los eventos asociados a cada boton y de realizar
     * sus correspondientes acciones e incocaciones a funciones o metodos si los hubiera
     */
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

        if (position == 0) {
            // spinnerCiudades.adapter=spinnerCiudadProveedor

        }
    }
}
