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
import android.widget.TextView
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_formulario_compra.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.util.*

class FormularioCompraActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    lateinit var registroCompra: Registro
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var adaptadorSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var adaptadorSpinnerCiudad: ArrayAdapter<CharSequence>
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder
    var bandera: Int = 0

    /**
     * Funcion encargada de ejecutar los metodos o funciones para la correpta implementacion
     * de las variables y sus relaciones dentro de este contexto
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_compra)



        cargarRegistro()
        mostrarCalendario()
        mostrarCuadroDialogo()
    }

    /**
     *Funcion encargada de llevar a cabo la asignacion  e inicializacion de valores a las variables
     *asociados al XML relacionado con esta actividad,para el correpto despliegue y
     * funcionamiento de la misma
     */
    fun cargarRegistro() {
        btnRegistrarFC.setOnClickListener(this)
        btnCancelarFC.setOnClickListener(this)
        btnFecha.setOnClickListener(this)

        registroCompra= Registro()

        var tipoProducto = arrayOf("Seleccione Opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionFC.adapter = adapter
        spinnerOpcionFC.onItemSelectedListener = this

        adapterSpinnerFrutas =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)

        adaptadorSpinnerVerduras =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)

        adaptadorSpinnerCiudad =
            ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
        spinnerCiudadFC.adapter = adaptadorSpinnerCiudad



    }


    /**
     * @dialogClickListener Variable que contiene la accion del DialogInterface.OnclickListener y
     * si la accion al boton del cuadro de dialogo mostrado es positivo o SI, llama a finish() y sale de
     * la actividad de edicion del registro en el que se encuentre.
     * La accion se lleva a cabo dentro de la funcion onClick cuando se presiona el
     * boton btnCancelar.
     * Funcion que contiene la variable que lleva a cabo la accion antes mencionada
     */
    fun mostrarCuadroDialogo() {

        dialogClickListener = DialogInterface.OnClickListener { dialog, which ->

            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    finish()
                }
            }
        }
    }


    /**
     * Funcion encargada de generar una accion al click de cualquiera de los dos botones.
     * Del boton btnRegistrar,accion que es la de  asignar nuevos valores a el objeto
     * registroCompra con los valores asociados en las variables del xml, enviando el objeto a la
     * InterfazPrincipalFragment para ser enviado a las actividades que lo requieran. La accion
     * del boton btnCancelar finaliza la actividad y regresa la llamada a la actividad
     * que la invoco.
     * @builder Variable de tipo AlertDialog.Builder encargada de construir el cuadro de dialogo que
     * le notifica al usuario en caso de seleccionar el boton cancelar edicion mediante un cuadro
     * de dialogo si desea abandonar la edicion actual o por el contrario decide seguir en dicha actividad
     */
    override fun onClick(v: View?) {

        if (v?.id == btnRegistrarFC.id) {

            if(!ediTxtPrecioLibraFC.text.isEmpty() && !ediTxtLibrasFC.text.isEmpty() && !ediTxtBultosFC.text.isEmpty()) {

                var spinnerOpciones: String = spinnerListaFC.selectedItem.toString()
                var precio: Int = ediTxtPrecioLibraFC.text.toString().toInt()
                var libras: Int = ediTxtLibrasFC.text.toString().toInt()
                var bultos: Int = ediTxtBultosFC.text.toString().toInt()
                var fechaRegistro: String = ediTxtFechaRegistroFC.text.toString()
                var procedencia: String = spinnerCiudadFC.selectedItem.toString()

                registroCompra = Registro()

                registroCompra.precio = precio
                registroCompra.libras = libras
                registroCompra.bultos = bultos
                registroCompra.fechaRegistro = fechaRegistro
                registroCompra.procedencia = procedencia
                registroCompra.nombre = spinnerOpciones

                registroCompra.tipoOpcion = spinnerOpcionFC.selectedItemPosition
                registroCompra.tipoLista = spinnerListaFC.selectedItemPosition


                /*El objeto registroCompra que sera enviado mediante el intent al fragment que contiene la logica del negocio
            InterfazPrincipalFragment con sus valores asociados para ser enviado a las actividades que lo requieran*/
                var intent = Intent()
                intent.putExtra("registroCompra", registroCompra)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }else{
                toast("error")
            }

        } else if (v?.id == btnCancelarFC.id) {

            builder.setMessage("Esta seguro").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
        } else if (v?.id == btnFecha.id) {
            mostrarCalendario()

        }
    }


    /**
     * Funcion encargada de mostrar el calendario para realizar el registro
     * del año, mes y dia de la fecha asociada a la compra
     */
    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val mont = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnFecha.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                ediTxtFechaRegistroFC.setText("" + dayOfMonth + "/" + mont + "/" + year)

            }, year, mont, day)
            dpd.show()
        }
    }

    /**
     * @param position La posicion asociada al arrayOf de elementos llevada a cabo en
     * la funcion cargarRegistro.
     * @spinnerCompra Nombre del Spinner del XML relacionado con esta actividad que contiene
     * valores asociados para ser desplegados en funcion de la posicion seleccionada dentro
     * de esta actividad.
     * Funcion encargada de escuchar y desplegar las acciones relacionadas al evento asociado
     * al spinnerCompraVF llevado a cabo en la funcion cargarRegistro
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFC.adapter = null
        } else if (position == 1) {
            spinnerListaFC.adapter = adapterSpinnerFrutas
        } else if (position == 2) {
            spinnerListaFC.adapter = adaptadorSpinnerVerduras
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
