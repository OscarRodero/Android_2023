package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Usuario
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity

object Conexion {
    var nombreBD = "administracion.db3"

    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }

    fun addPersona(contexto: AppCompatActivity, u: Usuario){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("ID", u.ID)
        registro.put("Nombre",u.Nombre)
        registro.put("Edad", u.Edad)
        bd.insert("usuarios", null, registro)
        bd.close()
    }

    fun delPersona(contexto: AppCompatActivity, ID: String):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("usuarios", "ID='${ID}'", null)
        bd.close()
        return cant
    }

    fun modPersona(contexto:AppCompatActivity, ID:String, u: Usuario):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Nombre", u.Nombre)
        registro.put("Edad", u.Edad)
        val cant = bd.update("usuarios", registro, "ID='${ID}'", null)
        bd.close()
        return cant
    }

    fun buscarPersona(contexto: AppCompatActivity, ID:String):Usuario? {
        var u:Usuario? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select Nombre,Edad from personas where ID='${ID}'",
            null
        )
        if (fila.moveToFirst()) {
            u = Usuario(ID, fila.getString(0), fila.getInt(1))
        }
        bd.close()
        return u
    }

    fun obtenerPersonas(contexto: AppCompatActivity):ArrayList<Usuario>{
        var usuarios:ArrayList<Usuario> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select ID,nombre,edad from usuarios", null)
        while (fila.moveToNext()) {
            var u:Usuario = Usuario(fila.getString(0),fila.getString(1),fila.getInt(2))
            usuarios
        }
        bd.close()
        return usuarios
    }

}