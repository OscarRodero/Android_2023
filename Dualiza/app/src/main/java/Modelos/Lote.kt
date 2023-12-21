package Modelos

import java.io.Serializable

data class Lote(var empresaEntregante: String, var objetosEntregados: ArrayList<Chatarra>, var estado: Boolean, var latitud: Double, var longitud: Double) : Serializable
