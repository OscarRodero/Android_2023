package Modelo

import java.util.Date

data class Alumno(var nombre:String, var apellidos:String, var fechaNacimiento: String, var curso:String, var asignaturas: MutableList<String>)
