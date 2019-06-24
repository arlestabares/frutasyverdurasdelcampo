package com.example.nadie.megafrutasyverduras.actividades

import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Producto
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import com.example.nadie.megafrutasyverduras.modelo.Registro
import com.example.nadie.megafrutasyverduras.util.ManagerFireBase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast


/**
 * @author Arles de Jesus Tabares Carvajal
 *
 */
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ManagerFireBase.onActualizarAdaptador {

    lateinit var listaProductos: ArrayList<Producto>
    lateinit var listaCompra: ArrayList<Registro>
    lateinit var listaProveedores: ArrayList<Proveedor>
    lateinit var listaStock: ArrayList<Registro>
    lateinit var listaParaDonacion: ArrayList<Registro>
    lateinit var managerFB:ManagerFireBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        ManagerFireBase.instanciar(this)
        managerFB = ManagerFireBase.instant!!
        managerFB.escucharEventoFireBase()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        listaProductos = ArrayList()
        listaCompra = ArrayList()
        listaProveedores = ArrayList()
        listaParaDonacion = ArrayList()
        listaStock = ArrayList()


        /*var bundle = Bundle()
        bundle.putParcelableArrayList("listaProductos", listaProductos)
        bundle.putParcelableArrayList("listaCompra", listaCompra)
        bundle.putParcelableArrayList("listaStock", listaStock)
        bundle.putParcelableArrayList("listaProveedores", listaProveedores)
        bundle.putParcelableArrayList("listaDonacionFV", listaParaDonacion)*/

        cardViewRC.setOnClickListener(this)
        cardViewLRC.setOnClickListener(this)
        cardViewRStock.setOnClickListener(this)
        cardViewLRStock.setOnClickListener(this)
        cardViewRD.setOnClickListener(this)
        cardViewLD.setOnClickListener(this)
        cardViewRP.setOnClickListener(this)
        cardViewLP.setOnClickListener(this)
        cardViewIA.setOnClickListener(this)

        //Esta variable bundle lleva consigo una lista de listaProductos y listaCompra la cual se envia
        // al fragment miFragment
        //var bundle = Bundle()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            //super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * Metodo encargado de los eventos o escuchas de los item seleccionados
     * del menu superior derecho de la interfaz principal de  opciones
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.itemId == R.id.informacionAplicacion) {
            val intent = Intent(this, InformacionFuncionalidadActivity::class.java)
            startActivity(intent)

        } else if (item.itemId == R.id.realizarDonacion) {
            val intent = Intent(this, RealizarDonacionesActivity::class.java)
            intent.putParcelableArrayListExtra("registroDonacion",listaParaDonacion)
           // intent.putExtra("registro_Desde_Activity_Main", listaParaDonacion)

            Log.e("Mensaje_desde_activity", listaParaDonacion.toString())
            startActivityForResult(intent, 100)

        } else if (item.itemId == R.id.centrosParaDonacion) {
            val intent = Intent(this, CentrosParaDonacionActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.donacionesRealizadas) {
            val intent = Intent(this, CentrosParaDonacionActivity::class.java)
            startActivity(intent)

        }

        /*else if (item.itemId == R.id.listarServicio) {

                   val intent = Intent(this, FormularioProveedorActivity::class.java)
                   startActivity(intent)

                   //con startActivityForResult se inicia una actividad esperando
                   // que nos devuelva algo,
                   //cuando la actividad envia su respuesta se invoca el método onActivityResult
               } else if (item.itemId == R.id.registrarCompraFV) {
                   val intent = Intent(this, FormularioCompraActivity::class.java)
                   //  intent.putExtra("registros",listaCompra)
                   // intent.putExtra("productos",listaProductos)
                   startActivity(intent)

               } else if (item.itemId == R.id.registroProveedor) {
                   val intent = Intent(this, FormularioProveedorActivity::class.java)
                   startActivity(intent)

               } else if (item.itemId == R.id.registrarDescomposicion) {
                   var intent = Intent(this, FormularioDonacionActivity::class.java)
                   // intent.putExtra("registros", listaCompra)
                   // intent.putExtra("productos", listaProductos)
                   startActivity(intent)

               } else if (item.itemId == R.id.registrarStock) {
                   val intent = Intent(this, FormularioStockActivity::class.java)
                   intent.putExtra("registros", listaCompra)
                   //intent.putExtra("productos",listaProductos)
                   startActivity(intent)

               }*/


        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
        //  return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        var isFragmentSelection: Boolean? = false
        var miFragment: Fragment? = null



        when (item.itemId) {
            R.id.menu_opciones -> {
                // Handle the camera action

              //  miFragment = InterfazPrincipalFragment()
                isFragmentSelection = true
                //fm.beginTransaction().replace(R.id.content_main,miFragment).commit()
            }
            R.id.nav_gallery -> {
              //  miFragment = InterfazPrincipalFragment()
                isFragmentSelection = true
                // fm.beginTransaction().replace(R.id.content_main,miFragment).commit()

            }
            R.id.operaciones -> {
              //  miFragment = InterfazPrincipalFragment()
                isFragmentSelection = true
                //  fm.beginTransaction().replace(R.id.content_main,miFragment).commit()


            }
            R.id.cerrarAplicacion -> {
                intent = Intent(ACTION_MAIN)
                intent.addCategory(CATEGORY_HOME)
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }

        }

        if (isFragmentSelection == true) {
            if (miFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, miFragment).addToBackStack(null)
                    .commit()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun registrarProducto(registro: Registro, bandera: Int) {

        if (bandera == 12) {
            //listaCompra.add(registro)
            managerFB.insertarCompra(registro)
        } else if (bandera == 14) {
            listaStock.add(registro)
            managerFB.insertarStock(registro)
        } else if (bandera == 16) {
            listaParaDonacion.add(registro)
            managerFB.insertarDonacion(registro)

        }

    }

    fun editarProducto(pos: Int, registro: Registro, bandera: Int) {

        if (bandera == 13) {
            listaCompra.set(pos, registro)
        } else if (bandera == 15) {
            listaStock.set(pos, registro)
        } else if (bandera == 17) {
            listaParaDonacion.set(pos, registro)
        }
    }


    fun registrarProveedor(registro: Proveedor) {
        listaProveedores.add(registro)
        managerFB.insertarProveedor(registro)

    }

    fun editarProveedor(pos: Int, registro: Proveedor) {
        listaProveedores.set(pos, registro)
    }

    override fun actualizarListaCompra(registro: Registro) {
        listaCompra.add(registro)
    }

    override fun actualizarListaProveedor(proveedor: Proveedor) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun actualizarListaStock(registro: Registro) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onClick(v: View?) {
        if (v == cardViewRC) {
            var intent = Intent(this, FormularioCompraActivity::class.java)
            // intent.putParcelableArrayListExtra("registros", listaCompra)
            startActivityForResult(intent, 12)
        } else if (v == cardViewLRC) {

            if(listaCompra.isEmpty()){
                toast("Debe ingresar Informacion a la lista por primera vez")
            }else {
                var intent = Intent(this, ListarCompraActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaCompra)
                startActivityForResult(intent, 13)
            }

        } else if (v == cardViewRStock) {
            var intent = Intent(this, FormularioStockActivity::class.java)
            // intent.putParcelableArrayListExtra("registros", listaStock)
            startActivityForResult(intent, 14)
        } else if (v == cardViewLRStock) {
            if (listaStock.isEmpty()){
                toast("Debe ingresar Informacion a la lista por primera vez")
            }else{
                var intent = Intent(this, ListarStockActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaStock)
                startActivityForResult(intent, 15)
            }
        } else if (v == cardViewRD) {
            var intent = Intent(this, FormularioDonacionActivity::class.java)
            // intent.putParcelableArrayListExtra("resgistros", listaParaDonacion)
            startActivityForResult(intent, 16)
        } else if (v == cardViewLD) {
            if (listaParaDonacion.isEmpty()){
                toast("Debe ingresar Informacion a la lista por primera vez")
            }else {
                var intent = Intent(this, ListarDonacionActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaParaDonacion)
                Log.e("registroInterfazPricipa",listaParaDonacion.toString())
                startActivityForResult(intent, 17)
            }
        } else if (v == cardViewRP) {
            var intent = Intent(this, FormularioProveedorActivity::class.java)
            // intent.putExtra("registros", listaProveedores)
            startActivityForResult(intent, 18)
        } else if (v == cardViewLP) {
            if (listaProveedores.isEmpty()){
                toast("Debe ingresar Informacion a la lista por primera vez")
            }else {
                var intent = Intent(this, ListarProveedoresActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaProveedores)
                startActivityForResult(intent, 19)
            }
        } else if (v == cardViewIA) {
            var intent = Intent(this, InformacionFuncionalidadActivity::class.java)
            //  intent.putExtra("registros", listaCompra)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                var listaCompra = data?.getParcelableExtra<Registro>("registroCompra")
                registrarProducto(listaCompra!!, 12)
            }
        } else if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                var editarCompra = data?.getParcelableExtra<Registro>("registro")
                var pos = data?.getIntExtra("posicion", 0)
                editarProducto(pos!!, editarCompra!!, 13)
            }

        } else if (requestCode == 14) {
            if (resultCode == Activity.RESULT_OK) {
                var registrarStock = data?.getParcelableExtra<Registro>("registroFormularioStock")
                registrarProducto(registrarStock!!, 14)
                Log.e("registrostock", registrarStock.toString())
            }
        } else if (requestCode == 15) {
            if (resultCode == Activity.RESULT_OK) {
                var editarStock = data?.getParcelableExtra<Registro>("registroDesdeListarStock")
                var pos = data?.getIntExtra("posicion", 0)
                editarProducto(pos!!, editarStock!!, 15)
            }

        } else if (requestCode == 16) {
            if (resultCode == Activity.RESULT_OK) {
                var registrarDonacion = data?.getParcelableExtra<Registro>("registroParaDonacion")
                registrarProducto(registrarDonacion!!, 16)
                Log.e("registroParaDonacion", registrarDonacion.toString())
            }

        } else if (requestCode == 17) {
            if (resultCode == Activity.RESULT_OK) {
                val editarDonacion = data?.getParcelableExtra<Registro>("registroDesdeListarDonacion")
                val posicion = data?.getIntExtra("posicion", 0)
                editarProducto(posicion!!, editarDonacion!!, 17)
            }
        } else if (requestCode == 18) {
            if (resultCode == Activity.RESULT_OK) {
                var listaProveedores = data?.getParcelableExtra<Proveedor>("registroProveedor")
                registrarProveedor(listaProveedores!!)
            }
        } else if (requestCode == 19) {
            if (resultCode == Activity.RESULT_OK) {
                var editarRegistroProveedores = data?.getParcelableExtra<Proveedor>("registroDesdeListarProveedor")
                var pos = data?.getIntExtra("posicion", 0)
                editarProveedor(pos!!, editarRegistroProveedores!!)
            }

        } else if (requestCode == 100) {

            if(resultCode == Activity.RESULT_OK) {

                var libras = data?.getIntExtra("libras", 0)
                var opcionFV = data?.getIntExtra("opcionFV", 0)
                var listaFV = data?.getIntExtra("listaFV", 0)

                Log.e("libras_r", libras.toString())

                for (registro in listaParaDonacion) {
                    if(registro.tipoOpcion == opcionFV && registro.tipoLista == listaFV){
                        if (registro.libras-libras!! > 0) {
                            registro.libras = registro.libras-libras
                            Toast.makeText(this, "Debe Ingresar menor a las libras que existen", Toast.LENGTH_LONG).show()
                            break;
                        }
                    }
                }

            }

        }

    }

}



