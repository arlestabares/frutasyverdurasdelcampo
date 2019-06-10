package com.example.nadie.megafrutasyverduras.editarFormularios

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_editar_compra.*
import java.util.*
import android.app.AlertDialog
import android.content.DialogInterface


/**
 * @author Arles de Jesus Tabares Carvajal
 *
 */
class EditarCompraActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    var posicion: Int = 0
    lateinit var registroCompra: Registro
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerCiudad: ArrayAdapter<CharSequence>
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder

    /**
     * Funcion principal, encargada de la ejecucion de todo el codigo generado
     * en este contexto,ejecuta los metodos o funciones que contiene haciendo las llamadas
     * correspondientes a cada uno de ellos.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_compra)


        cargarRegistrosAnteriores()
        mostrarCalendario()
        mostrarCuadroDeDialogo()

    }

    /**
     * Funcion encargada de recibir el objeto registroCompra  enviado desde
     * el AdapterCompra y asignar los valores a las variables del xml con los nuevos valores que
     * que tiene el objeto registroCompra, Los adaptadoresSpinner  de fruta y verdura asignan
     * una lista asociada a cada uno de ellos con los valores correspondientes.
     * @registroCompra  variable que contiene la lista de los objetos que se inflaron en AdapterCompra
     * y que fueron enviados para ser editados en esta actividad con sus respectivos valores asociados.
     * @posicion variable global que contiene la posicion del objeto en  la lista que es inflado
     * en AdapterCompra.
     * y que es necesaria para saber cual es la posicion donde esta el objeto a editar en esta activida.
     * @tipoProducto Variable local, contiene un arrayOf de tres elementos para ser seleccionados.
     * @adapter Variable local, de tipo ArrayAdapter al cual se le asignara el la variable tipoProducto
     * con un tipo de lista.
     * @posCiudad Variable local al metodo, es necesaria para ubicar la posicion en la que fue seleccionada
     * la ciudad  en la lista del spinner en el adapterCompra y asignarselo a la nueva variable
     * spinnerProcedencia de este contexto que contendra la ciudad de donde se envia el pedido
     * de la fruta o verdura
     */

    fun cargarRegistrosAnteriores() {

        btnEditarRegistroEC.setOnClickListener(this)
        btnCancelarRegistroEC.setOnClickListener(this)

        /*intent provenientes de AdapterCompra,la respuesta al activityForResult solicitada en el AdapterCompra
         la dara el metodo onClick() con el objeto registroCompra y la posicion del mismo cuando se seleccione
         el boton btnEditarRegistro*/
        registroCompra = intent.getParcelableExtra("listaDesdeAdapterCompra")
        posicion = intent.getIntExtra("posicionObjeto", 0)

        ediTxtPrecioLibraEC.setText(registroCompra.precio.toString())
        ediTxtLibrasEC.setText(registroCompra.libras.toString())
        ediTxtBultosEC.setText(registroCompra.bultos.toString())


        var tipoProducto = arrayOf("Seleccione Opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionEC.adapter = adapter

        /*Aqui le asignamos el valor al spinnerOpcionEC y al spinnerListaEC antes de las escuchas como tal,
        ya que primero debemos cargar los valores que le llegan con el registroCompra y despues
        le asignamos las escuchas para capturar nuevamente alguna seleccion por parte del usuario
        De lo contrario no cargaria correptamente los valores asociados anteriormente en el registro*/
        spinnerOpcionEC.setSelection(registroCompra.tipoOpcion)


        /*Despues de asignarle los valores correspondientes a cada uno de los spinner anteriores, se puede
        asignar las escuchas con el onItemSelectedListener para su correpto funcionamiento.*/

        spinnerOpcionEC.onItemSelectedListener = this
        spinnerListaEC.onItemSelectedListener = this
        spinnerProcedenciaEC.onItemSelectedListener = this

        adapterSpinnerCiudad =
            ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
        spinnerProcedenciaEC.adapter = adapterSpinnerCiudad

        var posCiudad = adapterSpinnerCiudad.getPosition(registroCompra.procedencia)
        spinnerProcedenciaEC.setSelection(posCiudad)


    }

    /**
     * @dialogClickListener Variable que contiene la accion del DialogInterface.OnclickListener y
     * si la accion al boton del cuadro de dialogo mostrado es positivo o SI, llama a finish() y sale de
     * la actividad de edicion del registro en el que se encuentre.
     * La accion se lleva a cabo dentro de la funcion onClick cuando se presiona el
     * boton btnCancelarRegistro.
     * Funcion que contiene la variable que lleva a cabo la accion antes mencionada
     *
     */
    fun mostrarCuadroDeDialogo() {
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
     * Del boton btnEditarRegistro,accion que es la de  asignar nuevos valores a el objeto
     * registroCompra con los valores de las variables del xml, enviando el objeto a la
     * InterfazPrincipalFragment para ser enviado a las actividades que lo requieran. La accion
     * del boton btnCancelarRegistro finaliza la actividad y regresa la llamada a la actividad
     * que la invoco.
     * @builder Variable de tipo AlertDialog.Builder encargada de construir el cuadro de dialogo que
     * le notifica al usuario en caso de seleccionar el boton cancelar edicion mediante un cuadro
     * de dialogo si desea abandonar la edicion actual o por el contrario decide seguir en dicha actividad
     */
    override fun onClick(v: View?) {

        if (v?.id == btnEditarRegistroEC.id) {

            registroCompra.tipoOpcion = spinnerOpcionEC.selectedItemPosition
            registroCompra.tipoLista = spinnerListaEC.selectedItemPosition
            registroCompra.precio = ediTxtPrecioLibraEC.text.toString().toInt()
            registroCompra.libras = ediTxtLibrasEC.text.toString().toInt()
            registroCompra.bultos = ediTxtBultosEC.text.toString().toInt()
            registroCompra.procedencia = spinnerProcedenciaEC.selectedItem.toString()
            registroCompra.fechaRegistro = ediTxtFechaRegistroEC.text.toString()
            registroCompra.nombre = spinnerListaEC.selectedItem.toString()


            /* intent que se envia a ListarCompraActivity como respuesta al ActivityForResult,
            ya que en el metodo cargarRegistro() de esta activity se recibe el objeto y la posicion, faltando
            una respuesta por enviar,
            ya que este espera una respuesta con un objeto registroCompra y una posicion para llevar a cabo la
            actualizacion del registro recien editado y la cual se le envia con el siguiente intent. Este intent
            tambien llega a InterfazPrincipalFragment para obtener un resultOK y poder editarlo */
            val intent = Intent()
            intent.putExtra("editarRegistro", registroCompra)
            intent.putExtra("posicion", posicion)
            setResult(Activity.RESULT_OK, intent)

            finish()

        } else if (v?.id == btnCancelarRegistroEC.id) {

            builder = AlertDialog.Builder(this)
            builder.setMessage("Està seguro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

        }
    }

    /**
     * Funcion encargada de mostrar el calendario para elegir una fecha del año, mes y dia
     * en el formulario de edicion del registro de la compra.
     */
    fun mostrarCalendario() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val mont = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        ediTxtFechaRegistroEC.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                ediTxtFechaRegistroEC.setText("" + dayOfMonth + "/" + month + "/" + year)
            }, year, mont, day)
            dpd.show()
        }
    }

    /**
     * @param parent
     * @param view
     * @param position Parametro que contiene la posicion seleccionada en el spinner spinnerVFE
     * @param id
     * Funcion encargada de generar una accion al evento de dar un click en alguno de los dos spinner
     * de seleccion de fruta o verdura que contiene la interfaz, capturando el valor seleccionado
     * por el usuario.
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaEC.adapter = null
        } else if (position == 1) {
            adapterSpinnerFrutas =
                ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)
            spinnerListaEC.adapter = adapterSpinnerFrutas
            spinnerListaEC.setSelection(registroCompra.tipoLista)

        } else if (position == 2) {

            adapterSpinnerVerduras =
                ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)
            spinnerListaEC.adapter = adapterSpinnerVerduras
            spinnerListaEC.setSelection(registroCompra.tipoLista)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
