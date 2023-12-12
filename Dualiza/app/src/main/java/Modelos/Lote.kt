package Modelos

import java.io.Serializable

data class Lote(var id:Int?, var empresa_entregante:User, var objetos_entregados:ArrayList<Chatarra>, var estado:Boolean): Serializable
