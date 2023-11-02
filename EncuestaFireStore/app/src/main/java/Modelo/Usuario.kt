package Modelo

import java.io.Serializable

data class Usuario(var correo: String, var contraseña: String, var rol: Int) : Serializable
