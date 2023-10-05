package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Persona
import android.content.ContentValues
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

object Conexion {
    var nombreBD = "administracion.db3"

    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }

    fun addPersona(contexto: AppCompatActivity, p: Persona){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("dni", p.dni)
        registro.put("nombre",p.nombre)
        registro.put("edad", p.edad.toString())
        bd.insert("personas", null, registro)
        bd.close()
    }

    fun delPersona(contexto: AppCompatActivity, dni: String):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("personas", "dni='${dni}'", null)
        bd.close()
        return cant
    }

    fun modPersona(contexto:AppCompatActivity, dni:String, p:Persona):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", p.nombre)
        registro.put("edad", p.edad)
        val cant = bd.update("personas", registro, "dni='${dni}'", null)
        bd.close()
        return cant
    }

    fun buscarPersona(contexto: AppCompatActivity, dni:String):Persona? {
        var p:Persona? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select nombre,edad from personas where dni='${dni}'",
            null
        )
        if (fila.moveToFirst()) {
            p = Persona(dni, fila.getString(0), fila.getInt(1))
        }
        bd.close()
        return p
    }

    fun obtenerPersonas(contexto: AppCompatActivity):ArrayList<Persona>{
        var personas:ArrayList<Persona> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select dni,nombre,edad from personas", null)
        while (fila.moveToNext()) {
            var p:Persona = Persona(fila.getString(0),fila.getString(1),fila.getInt(2))
            personas.add(p)
        }
        bd.close()
        return personas
    }

}