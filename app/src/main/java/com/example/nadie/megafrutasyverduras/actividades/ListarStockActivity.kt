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

    var pos: Int = 0
    var posEliminar:Int = 0
    var registro: Registro ?=null
    var registroEliminar:Registro?=null
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
                    pos =data!!.getIntExtra("posicion",0)
                    Log.e("registro_editado", registro.toString() + " " + pos)
                    adapter?.actualizarStock(pos, registro!!)
                    adapter?.notifyDataSetChanged()
                   

                }
            }
        }

    /**
     * @registro Contiene la lista de objetos
     * @pos Posicion en la cual sera actualizado el registro que contiene los objetos
     * Funcion encargada de llevar a cabo el envio del objeto registro al MainActivity
     * para su actualizacion correspondiente luego de ser editado dicho registro, ya que
     * al presionar el boton o ir  hacia atras en la pila de actividades
     * èsta aun esta sin resolver o sin actualizar, y para ello se remite nuevamente dicho objeto
     * para tales fines
     */
    override fun onBackPressed() {

        if (registro!=null && pos!=null){

            val intent=Intent()
            intent.putExtra("registroDesdeListarStock",registro)
            intent.putExtra("posicion",pos)
            setResult(Activity.RESULT_OK,intent)

        }
        super.onBackPressed()
    }
}
