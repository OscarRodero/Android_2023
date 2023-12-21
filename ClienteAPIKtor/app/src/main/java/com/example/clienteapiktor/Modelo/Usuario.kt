package com.example.clienteapiktor.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(@SerializedName("dni")
                   val dni: String? = null,

                   @SerializedName("nombre")
                   val nombre: String? = null,

                   @SerializedName("clave")
                   val clave: String? = null,

                   @SerializedName("tfno")
                   val tfno: String? = null) :Serializable{
}