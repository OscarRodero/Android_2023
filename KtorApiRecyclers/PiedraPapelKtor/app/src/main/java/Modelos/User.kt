package Modelos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
                @SerializedName("id") val id:Int,
                @SerializedName("Username") val Username:String,
                @SerializedName("Email") val Email:String,
                @SerializedName("Password") val Password:String,
                @SerializedName("IsAdmin") val IsAdmin:Boolean
):Serializable
