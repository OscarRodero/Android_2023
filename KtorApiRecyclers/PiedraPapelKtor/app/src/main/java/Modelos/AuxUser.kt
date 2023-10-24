package Modelos


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AuxUser(
    @SerializedName("Email") val Email:String,
    @SerializedName("Password") val Password:String
): Serializable
