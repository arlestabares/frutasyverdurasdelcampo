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
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_editar_descomposicion.*
import java.util.*

class EditarDonacionActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    var posicion: Int = 0
    lateinit var registroParaDonacion: Registro
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_descomposicion)


        cargarValores()
        mostrarCalendario()
        cuadroDeDialogo()
    }

    /**
     * Funcion encargada de inciar  los valores de los parametros del XML asociado a esta actividad
     */
    fun cargarValores() {

        btnEditarED.setOnClickListener(this)
        btnCancelarED.setOnClickListener(this)

        /*intent proveniente de AdapterDonacion el cual trae consigo un registroParaDonacion
        con  registros de Tipo  Registro*/
        registroParaDonacion = intent.getParcelableExtra("listaDesdeAdapter")
        posicion = intent.getIntExtra("posicionObjeto", 0)

        // Log.e("registros", registroCompra.toString())

        ediTxtLibrasED.setText(registroParaDonacion.libras.toString())
        ediTxtBultosED.setText(registroParaDonacion.bultos.toString())
        ediTxtFechaRegistroED.setText(registroParaDonacion.fechaRegistro)

        var tipoProducto = arrayOf("Seleccionar opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionED.adapter = adapter

        spinnerOpcionED.setSelection(registroParaDonacion.tipoOpcion)
        spinnerOpcionED.onItemSelectedListener = this


        adapterSpinnerFrutas =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)

        adapterSpinnerVerduras =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)

    }

    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnFecha.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                ediTxtFechaRegistroED.setText("" + dayOfMonth + "/" + month + "/" + year)

            }, year, month, day)
            dpd.show()
        }
    }

    /**
     * Funcion encargada de mostrar el cuadro de dialogo junto con la varibale builder
     * que se encuentra en el metodo onClick(), y se activira al presionar el boton de cancelar.
     */
    fun cuadroDeDialogo() {

        dialogClickListener=DialogInterface.OnClickListener { dialog, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE ->{
                    finish()
                }
            }

        }

    }

    /**
     * Funcion encargada de realizar los eventos asociados a los botones
     * que se activaran cuando el  usuario elija la opcion de editar o salir de
     * el contexto actual.
     */
    override fun onClick(v: View?) {

        if (v?.id == btnEditarED.id) {

            if (!spinnerOpcionED.selectedItem.toString().equals("Seleccionar opcion")
                &&!spinnerListaED.selectedItem.toString().equals("Seleccione Fruta")
                &&!spinnerListaED.selectedItem.toString().equals("Seleccione Verdura")
                &&!ediTxtLibrasED.text.isEmpty()
                &&!ediTxtBultosED.text.isEmpty()
                &&!ediTxtFechaRegistroED.text.isEmpty()){


                registroParaDonacion.nombre = spinnerListaED.selectedItem.toString()
                registroParaDonacion.libras = ediTxtLibrasED.text.toString().toInt()
                registroParaDonacion.bultos = ediTxtBultosED.text.toString().toInt()
                registroParaDonacion.fechaRegistro = ediTxtFechaRegistroED.text.toString()

                registroParaDonacion.tipoOpcion = spinnerOpcionED.selectedItemPosition
                registroParaDonacion.tipoLista = spinnerListaED.selectedItemPosition

                /* intent que se envia a ListarDonacionActivity como respuesta al ActivityForResult,
                ya que en el metodo cargarRegistro() de esta activity se recibe el objeto y la posicion, faltando
                una respuesta por enviar.
                ya que este espera una respuesta con un objeto registroParaDonacion y una posicion para llevar a cabo la
                actualizacion del registro recien editado y la cual se le envia con el siguiente intent. Este intent
                tambien llega a InterfazPrincipalFragment para obtener un resultOK y poder editarlo */
                val intent = Intent()
                intent.putExtra("editarDonacion", registroParaDonacion)
                intent.putExtra("posicionObjeto", posicion)
                setResult(Activity.RESULT_OK, intent)

                finish()
            }else{
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }



        } else if (v?.id == btnCancelarED.id) {

            builder = AlertDialog.Builder(this)
            builder.setMessage("Esta seguro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaED.adapter = null
        } else if (position == 1) {
            spinnerListaED.adapter = adapterSpinnerFrutas
            spinnerListaED.setSelection(registroParaDonacion.tipoLista)

        } else if (position == 2) {
            spinnerListaED.adapter = adapterSpinnerVerduras
            spinnerListaED.setSelection(registroParaDonacion.tipoLista)
        }

    }
}
