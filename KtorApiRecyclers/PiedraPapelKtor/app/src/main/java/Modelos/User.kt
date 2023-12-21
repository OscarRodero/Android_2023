package Modelos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
                @SerializedName("Username") val Username:String,
                @SerializedName("Email") val Email:String,
                @SerializedName("Password") val Password:String,
                @SerializedName("IsAdmin") val IsAdmin:Boolean
):Serializable
