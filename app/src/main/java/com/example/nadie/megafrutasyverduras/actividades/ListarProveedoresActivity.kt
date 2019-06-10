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
import com.example.nadie.megafrutasyverduras.adapter.AdapterProveedores
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ListarProveedoresActivity : AppCompatActivity() {

    var posicion: Int? = 0
    var registro: Proveedor? = null
    lateinit var recyclerView: RecyclerView
    var listaProveedores: ArrayList<Proveedor>? = null
    var adapter: AdapterProveedores? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_proveedor)

        //intent proveniente de InterfazPrincipalFragment
        listaProveedores = intent.getParcelableArrayListExtra("registros")

        recyclerView = findViewById(R.id.recyclerViewRegistroP)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterProveedores(this, listaProveedores!!)
        recyclerView.adapter = adapter


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2534) {
            if (resultCode == Activity.RESULT_OK) {
                registro = data?.getParcelableExtra("registro")
                posicion = data?.getIntExtra("posicion", 0)
                Log.e("registro_editado", registro.toString() + " " + posicion)

                adapter?.actualizarProveedor(posicion!!, registro!!)
                adapter?.notifyDataSetChanged()

            }

        }
    }

    override fun onBackPressed() {
        if (posicion != null && registro != null) {
            val intent = Intent()
            intent.putExtra("registroDesdeListarProveedor", posicion!!)
            intent.putExtra("posicion", registro)
            setResult(Activity.RESULT_OK, intent)
        }
        super.onBackPressed()
    }
}
