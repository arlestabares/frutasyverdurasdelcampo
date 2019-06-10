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
import com.example.nadie.megafrutasyverduras.adapter.AdapterDescomposicion
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ListarDescomposiconActivity : AppCompatActivity() {


    var pos: Int? = 0
    var registro: Registro? = null
    lateinit var recyclerView: RecyclerView
    var listaDescomposicion: ArrayList<Registro>? = null
    var adapter: AdapterDescomposicion? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_descomposicon)


        //intent proveniente de InterfazPrincipalFragment
        listaDescomposicion = intent.getParcelableArrayListExtra("registros")
        //  listaProductos=intent.getParcelableArrayListExtra("registroCompra productos")

        recyclerView = findViewById(R.id.recyclerRegistroDescomposicionFV)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterDescomposicion(this, listaDescomposicion!!)
        recyclerView.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1118) {
            if (resultCode == Activity.RESULT_OK) {
                registro = data?.getParcelableExtra("editarDescomposicion")
                pos= data!!.getIntExtra("posicion",0)
                Log.e("registro_editado", registro.toString() + " la posicion" + pos)

                adapter?.actualizarDescomposicion(pos!!,registro!!)
                adapter?.notifyDataSetChanged()

            }

        }
    }

    override fun onBackPressed() {

        if (registro != null && pos!=null  ){
            val intent=Intent()
            intent.putExtra("registroDesdeListarDescomposicion",registro!!)
            intent.putExtra("posicion", pos!!)
            setResult(Activity.RESULT_OK, intent)
        }
        super.onBackPressed()

    }
}
