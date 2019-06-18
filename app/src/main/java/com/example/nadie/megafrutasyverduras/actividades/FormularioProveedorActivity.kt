package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
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
        val v_year = c.get(Calendar.YEAR)
        val v_month = c.get(Calendar.MONTH)
        val v_day = c.get(Calendar.DAY_OF_MONTH)

        btnFecha.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                ediTxtFechaRegistroFS.setText("" + dayOfMonth + "/" + month + "/" + v_day)

            }, v_year, v_month, v_day)
            dpd.show()
        }

    }

    /**
     * Funcion encargada de escuchar los eventos asociados a cada boton y de realizar
     * sus correspondientes acciones e incocaciones a funciones o metodos si los hubiera
     */
    override fun onClick(v: View?) {


        if (v?.id == btnRegistrarFS.id) {

            if (!ediTxtNombreP.text.isEmpty()&&!ediTxtTelefonoP.text.isEmpty()
                && !spinnerCiudadP.selectedItem.toString().equals("Seleccione Ciudad de Procedencia")
                &&!ediTxtFechaRegistroFS.text.isEmpty()){

                registroProveedor = Proveedor()

                registroProveedor.nombre = ediTxtNombreP.text.toString()
                registroProveedor.ciudad = spinnerCiudadP.selectedItem.toString()
                registroProveedor.telefono = ediTxtTelefonoP.text.toString()
                registroProveedor.fechaRegistro = ediTxtFechaRegistroFS.text.toString()

                //  listaProveedores.add(registroProveedor)
                var intent = Intent()
                intent.putExtra("registroProveedor", registroProveedor)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }else{
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }



        }else if (v?.id == btnCancelarFS.id) {
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
