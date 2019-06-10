package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_formulario_descomposicion.*
import java.util.*
import kotlin.collections.ArrayList

class FormularioDescomposicionActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    lateinit var registro: Registro
    var listaDescomposicion: ArrayList<Registro>? = null
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerVerduras: ArrayAdapter<CharSequence>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_descomposicion)



        cargarRegistros()
        mostrarCalendario()

    }

    /**
     * Funcion encargada de cargar los registros iniciales del XML asociado a esta actividad,lo
     * mismo que de iniciar las escuchas necesarias para la ejecucion de los valores y registros asociados
     * a cada variable.
     *
     */
    fun cargarRegistros() {

        btnRegistrarFD.setOnClickListener(this)
        btnCancelarFD.setOnClickListener(this)

        var tipoProducto = arrayOf("Seleccione Opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)

        spinnerOpcionFD.adapter = adapter
        spinnerOpcionFD.onItemSelectedListener = this
        adapterSpinnerFrutas =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)
        adapterSpinnerVerduras =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)


    }

    /**
     * funcion encargada de disparar los eventos asociados a los botones
     * de la interfaz
     */
    override fun onClick(v: View?) {


        if (v?.id == btnRegistrarFD.id) {

            var spinnerLista: String = spinnerListaFD.selectedItem.toString()
            var libras: Int = ediTxtLibrasFD.text.toString().toInt()
            var bultos: Int = ediTxtBultosFD.text.toString().toInt()
            var fechaRegistro: String = ediTxtFechaRegistroFD.text.toString()

            registro = Registro()

            registro.nombre = spinnerLista
            registro.libras = libras
            registro.bultos = bultos
            registro.fechaRegistro = fechaRegistro
            registro.tipoOpcion = spinnerOpcionFD.selectedItemPosition
            registro.tipoLista = spinnerListaFD.selectedItemPosition


            var intent = Intent()
            intent.putExtra("registroDescomposicion", registro)
            setResult(Activity.RESULT_OK, intent)
            finish()

        } else if (v?.id == btnCancelarFD.id) {
            finish()
        }
    }

    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        ediTxtFechaRegistroFD.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                ediTxtFechaRegistroFD.setText("" + dayOfMonth + "/" + month + "/" + year)

            }, year, month, day)
            dpd.show()
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFD.adapter = null
        } else if (position == 1) {
             spinnerListaFD.adapter = adapterSpinnerFrutas
        } else if (position == 2) {
           spinnerListaFD.adapter = adapterSpinnerVerduras
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
