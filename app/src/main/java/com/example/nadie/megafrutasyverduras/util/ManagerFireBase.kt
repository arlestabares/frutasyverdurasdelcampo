package com.example.nadie.megafrutasyverduras.util

import android.app.Activity
import android.util.Log
import com.example.nadie.megafrutasyverduras.modelo.Producto
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import com.example.nadie.megafrutasyverduras.modelo.Registro
import com.google.firebase.database.*

/**
 *
 */
object ManagerFireBase {


    var database: FirebaseDatabase? = null
    var dataRef: DatabaseReference? = null
    var instant: ManagerFireBase? = null
    var listener: onActualizarAdaptador? = null


    interface onActualizarAdaptador {
        fun actualizarListaCompra(registro: Registro)
        fun actualizarListaProveedor(proveedor: Proveedor)
        fun actualizarListaStock(registro: Registro)
    }

    /**
     *
     */
    fun  inicializar(actividad:Activity):ManagerFireBase{
        val instant:ManagerFireBase= ManagerFireBase
        instant.database= FirebaseDatabase.getInstance()
        instant.dataRef=database!!.reference

        listener = actividad as onActualizarAdaptador

        return instant

    }

    /**
     *
     */
    fun instanciar(actividad:Activity){
        if (instant==null){
            instant=inicializar(actividad)

        }
    }

    /**
     *
     */
    fun insertarCompra(registro: Registro){
        dataRef!!.child("registro").push().setValue(registro)

    }
    fun insertarDonacion(registro: Registro){

        dataRef!!.child("donacion").push().setValue(registro)
    }
    fun insertarProveedor(proveedor: Proveedor){
        dataRef!!.child("proveedor").push().setValue(proveedor)

    }

    fun insertarStock(registro: Registro){
        dataRef!!.child("stock").push().setValue(registro)

    }
    fun insertarProducto(producto: Producto){
        dataRef!!.child("producto").push().setValue(producto)
    }

    fun escucharEventoFireBase(){
        dataRef!!.child("registro").addChildEventListener(object:ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /*
            Lee el evento de inserciòn en firebase, captura la llave que se genera automàticamente para el registro
             */
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val reg:Registro = p0.getValue(Registro::class.java)!!
                reg.id = p0.key!!
                listener!!.actualizarListaCompra(reg)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }


}