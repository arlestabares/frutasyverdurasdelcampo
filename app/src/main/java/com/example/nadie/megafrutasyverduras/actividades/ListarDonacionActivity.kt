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
import com.example.nadie.megafrutasyverduras.adapter.AdapterDonacion
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ListarDonacionActivity : AppCompatActivity() {


    var pos: Int? = 0
    var registro: Registro? = null
    lateinit var recyclerView: RecyclerView
    var listaParaDonacion: ArrayList<Registro>? = null
    var adapter: AdapterDonacion? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_descomposicon)


        //intent proveniente de InterfazPrincipalFragment
        listaParaDonacion = intent.getParcelableArrayListExtra("registros")
        //  listaProductos=intent.getParcelableArrayListExtra("registroCompra productos")
        Log.e("ListarDonacionActivity",listaParaDonacion.toString())

        recyclerView = findViewById(R.id.recyclerRegistroDescomposicionFV)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterDonacion(this, listaParaDonacion!!)
        recyclerView.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*requestCode 1118 generado en AdapterDonacion en la funcion Onclick(),y que es enviado  mediante
          el intent a la actividad  EditarDonacionActivity,y es dicha actividad la encargada de enviar la
          respuesta de ActivityResult_OK  a este contexto o ListarDonacionActivity,
          mediante la llave editarDonacion recibido en el siguiente  if */
        if (requestCode == 1118) {
            if (resultCode == Activity.RESULT_OK) {
                registro = data?.getParcelableExtra("editarDonacion")
                pos= data!!.getIntExtra("posicion",0)
                Log.e("registro_editadoListarD", registro.toString() + " la posicion = " + pos)

                adapter?.actualizarDescomposicion(pos!!,registro!!)
                adapter?.notifyDataSetChanged()

            }

        }
    }

    override fun onBackPressed() {

        if (registro != null && pos!=null  ){
            val intent=Intent()
            intent.putExtra("registroDesdeListarDonacion",registro!!)
            intent.putExtra("posicion", pos!!)
            setResult(Activity.RESULT_OK, intent)
        }
        super.onBackPressed()

    }
}
