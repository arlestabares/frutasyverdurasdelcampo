package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_realizar_donaciones.*

class RealizarDonacionesActivity() : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    lateinit var adapterSpinnerFundaciones: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerFruta: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerVerdura: ArrayAdapter<CharSequence>
    var registroDonacion: ArrayList<Registro>? = null
    var libras: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_donaciones)

        btnCantidadDonar.setOnClickListener(this)
        btnDonar.setOnClickListener(this)

        //registroDonacion = intent.getParcelableExtra("registro_Desde_Activity_Main")
        registroDonacion = intent.getParcelableArrayListExtra("registroDonacion")
        Log.e("lista donacion", registroDonacion.toString())


        var tipoProducto = arrayOf("Seleccione Opcion Fruta o Verdura", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionFV.adapter = adapter
        spinnerOpcionFV.onItemSelectedListener = this

        adapterSpinnerFundaciones =
            ArrayAdapter.createFromResource(this, R.array.Lista_fundaciones, android.R.layout.simple_list_item_1)
        adapterSpinnerFruta =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)
        adapterSpinnerVerdura =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)

        spinnerFundacionParaDonar.adapter = adapterSpinnerFundaciones

    }

    override fun onClick(v: View?) {


        if (v?.id == btnCantidadDonar.id) {

            // ediTxtCantidad.setText(registroDonacion.libras.toString())

            for (registro in registroDonacion!!) {

                if (registro.tipoOpcion == spinnerOpcionFV.selectedItemPosition && registro.tipoLista == spinnerListaFV.selectedItemPosition) {
                    libras += registro.libras
                }

            }

            Toast.makeText(this, "La cantidad de libras disponibles para donar es = " + libras, Toast.LENGTH_LONG)
                .show()

        }
        else {
            var intent = Intent()
            intent.putExtra("libras", ediTxtCantidad.text.toString().toInt())
            intent.putExtra("opcionFV", spinnerOpcionFV.selectedItemPosition)
            intent.putExtra("listaFV", spinnerListaFV.selectedItemPosition)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFV.adapter = null
        } else if (position == 1) {
            spinnerListaFV.adapter = adapterSpinnerFruta

        } else if (position == 2) {
            spinnerListaFV.adapter = adapterSpinnerVerdura

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
