package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(val id:String, val name:String, val type:String)
