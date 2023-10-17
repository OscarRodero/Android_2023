package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(val id:String, val nombre:String, val tipo:String)
