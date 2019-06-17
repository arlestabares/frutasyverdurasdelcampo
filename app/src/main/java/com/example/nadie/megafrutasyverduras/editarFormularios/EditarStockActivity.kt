package com.example.nadie.megafrutasyverduras.editarFormularios

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
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_editar_stock.*
import java.util.*

class EditarStockActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    var pos: Int = 0
    lateinit var registroStock: Registro
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_stock)

        btnEditarES.setOnClickListener(this)
        btnCancelarES.setOnClickListener(this)


        cargarValores()
        mostarCalendario()
        cuadroDeDialogo()
    }


    /**
     * Funcion encargada de inicializar los valores asociados a cada uno de las variables del
     * XML asociado a esta actividad
     */
    fun cargarValores() {

        //intent proveniente de AdapterStock
        registroStock = intent.getParcelableExtra("listaRegistrosStockAdapter")
        pos = intent.getIntExtra("posicion", 0)

        ediTxtLibrasES.setText(registroStock.libras.toString())
        ediTxtBultosES.setText(registroStock.bultos.toString())
        ediTxtFechaRegistroES.setText(registroStock.fechaRegistro)


        var tipoProducto = arrayOf("SeleccioneOpcin", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionES.adapter = adapter

        spinnerOpcionES.setSelection(registroStock.tipoOpcion)
        spinnerOpcionES.onItemSelectedListener = this

    }

    /**
     * Funcion encargada de mostrar el calendario que el usuario utilizara para
     * ingresar la fecha de resgistro de la edicion en este contexto
     */
    fun mostarCalendario() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnFecha.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                ediTxtFechaRegistroES.setText("" + dayOfMonth + "/" + month + "/" + year)
            }, year, month, day)
            dpd.show()
        }
    }

    /**
     * Funcion encargada de mostrar el cuadro de dialogo como alerta
     * para que el usuario decida seguir en el registro actual o salir de el
     * y regresar a la actividad que invoco la llamada a dicha actividad para la edicion
     */
    fun cuadroDeDialogo() {

        dialogClickListener = DialogInterface.OnClickListener { dialog, which ->

            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    finish()
                }
            }
        }
    }

    /**
     * Funcion encargada de cargar los valores reales con los valores que hay en los
     * ediText para ser registrados y enviados nuevamente al AdapterStock, que
     * sera encargado de actualizar los datos de los registros con los que de aqui se le envian
     * para que infle la lista con los nuevos valores actualizados.
     */
    override fun onClick(v: View?) {

        if (v?.id == btnEditarES.id) {


            registroStock.libras = ediTxtLibrasES.text.toString().toInt()
            registroStock.bultos = ediTxtBultosES.text.toString().toInt()
            registroStock.fechaRegistro = ediTxtFechaRegistroES.text.toString()
            registroStock.tipoOpcion = spinnerOpcionES.selectedItemPosition
            registroStock.tipoLista = spinnerListaES.selectedItemPosition
            registroStock.nombre = spinnerListaES.selectedItem.toString()

            /*Intent con destino a la actividad ListarStockActivity, encargada de verificar
              que este objeto que le llega se envie de nuevo al AdapterStock para su actualizacion
              correspondiente y asi inflar nuevamente la lista con el registro actualizado. */
            var intent = Intent()
            intent.putExtra("editarStock", registroStock)
            intent.putExtra("posicion", pos)
            setResult(Activity.RESULT_OK, intent)
            finish()

        } else if (v?.id == btnCancelarES.id) {

            builder = AlertDialog.Builder(this)
            builder.setMessage("Esta seguro de Salir").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
        }
    }

    /**
     * Funcion encargada de la escucha de los eventos asociados a los botones de los
     * espinners y de cargar la lista de registros correspondientes a cada uno de ellos.
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaES.adapter = null
        } else if (position == 1) {
            adapterSpinnerFrutas =
                ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)
            spinnerListaES.adapter = adapterSpinnerFrutas
            spinnerListaES.setSelection(registroStock.tipoLista)
        } else if (position == 2) {
            adapterSpinnerVerduras =
                ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)
            spinnerListaES.adapter = adapterSpinnerVerduras
            spinnerListaES.setSelection(registroStock.tipoLista)

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
