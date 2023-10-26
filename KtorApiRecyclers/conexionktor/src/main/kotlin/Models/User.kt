package Models

import kotlinx.serialization.Serializable

@Serializable
data class User(val Username:String, val Email:String, val Password:String, val IsAdmin:Boolean)
