package Modelo

import com.google.gson.annotations.SerializedName

data class Pokemon(
                    @SerializedName("id") val id: String,
                    @SerializedName("name") val name:String,
                    @SerializedName("type") val type:String
    )
