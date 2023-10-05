package Modelos

import java.io.Serializable

data class Encuesta(var Nombre:String, var SO:String, var ListaEspecialidades: ArrayList<String>, var HorasEstudio:Int):Serializable
