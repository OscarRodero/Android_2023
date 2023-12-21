package Auxiliar

import Conexion.AdminSQLiteConexion
import Modelos.Configuracion
import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

object Conexion {
    var nombreBD = "administracion.db3"

    fun addConfiguracion(contexto: AppCompatActivity, c: Configuracion){
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("modo", c.modo)
        registro.put("sesion", c.sesion)
        bd.insert("configuracion", null, registro)
        bd.close()
    }

    fun guardarConfiguracion(contexto: AppCompatActivity, configuracion: Configuracion) {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val usuarioActual = obtenerUsuarioActual()
        val cursor = bd.rawQuery("SELECT * FROM configuracion WHERE usuario=?", arrayOf(usuarioActual))

        if (cursor.moveToFirst()) {
            // El usuario ya tiene una configuración, actualizarla
            val registro = ContentValues()
            registro.put("modo", configuracion.modo)
            registro.put("sesion", if (configuracion.sesion) 1 else 0) // 1 para sesion activa, 0 para sesion inactiva
            bd.update("configuracion", registro, "usuario=?", arrayOf(usuarioActual))
        } else {
            // El usuario no tiene una configuración, insertar una nueva
            val registro = ContentValues()
            registro.put("usuario", usuarioActual)
            registro.put("modo", configuracion.modo)
            registro.put("sesion", if (configuracion.sesion) 1 else 0)
            bd.insert("configuracion", null, registro)
        }

        cursor.close()
        bd.close()
    }


    fun obtenerUsuarioActual(): String? {
        val firebaseAuth = FirebaseAuth.getInstance()
        val usuario = firebaseAuth.currentUser
        return usuario?.email
    }

    @SuppressLint("Range")
    fun obtenerConfiguracion(contexto: AppCompatActivity, usuario: String): Configuracion? {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM configuracion WHERE usuario=?", arrayOf(usuario))

        val configuracion: Configuracion?

        if (cursor.moveToFirst()) {
            val modo = cursor.getInt(cursor.getColumnIndex("modo")) == 1
            val sesion = cursor.getInt(cursor.getColumnIndex("sesion")) == 1
            configuracion = Configuracion(modo, sesion)
        } else {
            configuracion = null
        }

        cursor.close()
        bd.close()

        return configuracion
    }
}