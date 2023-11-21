package Modelos

import java.io.Serializable

data class Lote(var empresa_entregante:User, var objetos_entregados:ArrayList<Chatarra>): Serializable
