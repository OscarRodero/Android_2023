package Modelos

import java.io.Serializable

data class User(var email:String, var name:String, var password:String, var permissions:Int):Serializable
