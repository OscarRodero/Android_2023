package Modelo

import com.google.gson.annotations.SerializedName

data class Pokemon(
                    @SerializedName("id") val id: String,
                    @SerializedName("name") val nombre:String,
                    @SerializedName("tipo") val tipo:String
    )
