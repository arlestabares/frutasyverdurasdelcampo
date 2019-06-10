package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.adapter.AdapterStock
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ListarStockActivity(var adapter: AdapterStock? = null) : AppCompatActivity() {

    var pos: Int? = 0
    var registro: Registro ?=null
    lateinit var recyclerView: RecyclerView
    var listaStock: ArrayList<Registro>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_stock)

        //intent proveniente de InterfazPrincipalFragment
        listaStock = intent.getParcelableArrayListExtra("registros")

        recyclerView = findViewById(R.id.recylerRegistroStock)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterStock(this, listaStock!!)
        recyclerView.adapter = adapter


    }

    /**
     * Funcion encargada de
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1825) {
            if (resultCode == Activity.RESULT_OK) {
                registro= data?.getParcelableExtra("editarStock")
                pos =data?.getIntExtra("posicion",0)
                Log.e("registro_editado", registro.toString() + " " + pos)
                adapter?.actualizarCompra(pos!!, registro!!)
                adapter?.notifyDataSetChanged()

            }
        }
    }

    /**
     * Funcion encargada de
     *
     */
    override fun onBackPressed() {

        if (registro!=null && pos !=null){

            val intent=Intent()
            intent.putExtra("registroDesdeListarStock",registro)
            intent.putExtra("posicion",pos!!)
            setResult(Activity.RESULT_OK,intent)
        }
        super.onBackPressed()
    }
}
