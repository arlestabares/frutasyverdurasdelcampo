package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import com.example.nadie.megafrutasyverduras.util.ManagerFireBase
import kotlinx.android.synthetic.main.activity_formulario_stock.*
import java.util.*

/**
 * Clase encargada de agregar los datos o registros asociados a el formulario StockActivity
 *
 * actualizacio para demostracion
 */
class FormularioStockActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    lateinit var registroStock: Registro
    lateinit var adapterSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var dialogOnClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder
    lateinit var ejemplo :Registro


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_stock)


        cargarRegistros()
        mostrarCalendario()
    }

    fun cargarRegistros() {

        btnRegistrarFS.setOnClickListener(this)
        btnCancelarFS.setOnClickListener(this)

        var tipoProducto = arrayOf("Seleccione opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionFS.adapter = adapter
        spinnerOpcionFS.onItemSelectedListener = this


        adapterSpinnerVerduras =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)

        adapterSpinnerFrutas =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)

    }

    /**
     *
     */
    fun cuadroDeDialogo() {
        dialogOnClickListener = DialogInterface.OnClickListener { dialog, which ->


            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    finish()
                }

            }
        }

    }


    /**
     * Funcion encargada de disparar los eventos sobre los botones
     * dentro de la interfaz con sus respectivas acciones
     */
    override fun onClick(v: View?) {


        if (v?.id == btnRegistrarFS.id) {


            if (!spinnerOpcionFS.selectedItem.toString().equals("Seleccione opcion")
                && !spinnerListaFS.selectedItem.toString().equals("Seleccione Fruta")
                && !spinnerListaFS.selectedItem.toString().equals("Seleccione Verdura")
                && !ediTxtLibrasFS.text.isEmpty()
                && !ediTxtBultosFS.text.isEmpty()
                && !ediTxtFechaRegistroFS.text.toString().isEmpty()) {


                registroStock = Registro()

                registroStock.nombre = spinnerListaFS.selectedItem.toString()
                registroStock.libras = ediTxtLibrasFS.text.toString().toInt()
                registroStock.bultos = ediTxtBultosFS.text.toString().toInt()
                registroStock.fechaRegistro = ediTxtFechaRegistroFS.text.toString()

                registroStock.tipoOpcion = spinnerOpcionFS.selectedItemPosition
                registroStock.tipoLista = spinnerListaFS.selectedItemPosition

                var intent = Intent()
                intent.putExtra("registroFormularioStock", registroStock)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }else{
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }


        } else if (v?.id == btnCancelarFS.id) {
            builder.setMessage("Esta seguro").setPositiveButton("OK", dialogOnClickListener).show()

        }
    }

    /**
     * Funcion encargada de mostrar el calendario al usuario para que realice el
     * registro con la fecha necesaria para ello
     */
    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val v_year = c.get(Calendar.YEAR)
        val v_month = c.get(Calendar.MONTH)
        val v_day = c.get(Calendar.DAY_OF_MONTH)

        btnFecha.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                ediTxtFechaRegistroFS.setText("" + dayOfMonth + "/" + month + "/" + year)
            }, v_day, v_month, v_year)
            dpd.show()
        }

    }

    /**
     * Funcion encargada de las escuchas de los eventos asociados a los spinner
     * del XML asociado a esta actividad.
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFS.adapter = null
        } else if (position == 1) {
            spinnerListaFS.adapter = adapterSpinnerFrutas
        } else if (position == 2) {
            spinnerListaFS.adapter = adapterSpinnerVerduras
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
