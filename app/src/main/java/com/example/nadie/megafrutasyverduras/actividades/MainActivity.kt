package com.example.nadie.megafrutasyverduras.actividades

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.fragments.InterfazPrincipalFragment
import com.example.nadie.megafrutasyverduras.modelo.Producto
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, RegistroEscucha {


    lateinit var miFragment: InterfazPrincipalFragment
    lateinit var listaProductos: ArrayList<Producto>
    lateinit var listaCompra: ArrayList<Registro>
    lateinit var listaProveedores: ArrayList<Proveedor>
    lateinit var listaStock: ArrayList<Registro>
    lateinit var listaDescomposicion: ArrayList<Registro>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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
        listaDescomposicion = ArrayList()
        listaStock = ArrayList()

        // Aqui se agrega el Fragment InterfazPrincipalFragment como la  primer GUI al iniciarse la aplicacion
        //Para que el usuario escoja las opciones de navegacion.
        miFragment = InterfazPrincipalFragment()
        miFragment.listener = this

        var bundle = Bundle()
        bundle.putParcelableArrayList("listaProductos", listaProductos)
        bundle.putParcelableArrayList("listaCompra", listaCompra)
        bundle.putParcelableArrayList("listaStock", listaStock)
        bundle.putParcelableArrayList("listaProveedores", listaProveedores)
        bundle.putParcelableArrayList("listaDescomposicionFV", listaDescomposicion)

        miFragment.arguments = bundle
        getSupportFragmentManager().beginTransaction().add(R.id.content_main, miFragment).commit()

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

        } /*else if (item.itemId == R.id.listarServicio) {

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
            var intent = Intent(this, FormularioDescomposicionActivity::class.java)
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

                miFragment = InterfazPrincipalFragment()
                isFragmentSelection = true
                //fm.beginTransaction().replace(R.id.content_main,miFragment).commit()
            }
            R.id.nav_gallery -> {
                miFragment = InterfazPrincipalFragment()
                isFragmentSelection = true
                // fm.beginTransaction().replace(R.id.content_main,miFragment).commit()

            }
            R.id.operaciones -> {
                miFragment = InterfazPrincipalFragment()
                isFragmentSelection = true
                //  fm.beginTransaction().replace(R.id.content_main,miFragment).commit()


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

    override fun registrarProducto(registro: Registro, bandera: Int) {

        if (bandera == 12) {
            listaCompra.add(registro)
        } else if (bandera == 14) {
            listaStock.add(registro)
        } else if (bandera == 16) {
            listaDescomposicion.add(registro)
        }

    }

    override fun editarProducto(pos: Int, registro: Registro, bandera: Int) {

        if (bandera == 13) {
            listaCompra.set(pos, registro)
        } else if (bandera == 15) {
            listaStock.set(pos, registro)
        } else if (bandera == 17) {
            listaDescomposicion.set(pos, registro)
        }
    }


    override fun registrarProveedor(registro: Proveedor) {
        listaProveedores.add(registro)
    }

    override fun editarProveedor(pos: Int, registro: Proveedor) {
        listaProveedores.set(pos, registro)
    }

}

/**
 * Interfaz encargada de actualizar y mantener las listas siempre en un estado
 * disponibles con sus respectivos registros, ya que desde esta actividad son reenviadas
 * todas las listas a la InterfazPrincipalFragment para que desde alli sean
 * reenviadas a las activitys solicitantes.
 */
interface RegistroEscucha {

    fun registrarProducto(registro: Registro, bandera: Int)
    fun editarProducto(pos: Int, registro: Registro, bandera: Int)

    fun registrarProveedor(registro: Proveedor)
    fun editarProveedor(pos: Int, registro: Proveedor)


}

