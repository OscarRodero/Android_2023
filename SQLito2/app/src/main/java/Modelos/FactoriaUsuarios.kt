package Modelos

import kotlin.random.Random

object FactoriaUsuarios {
    fun generarUsuarios(x: Int): ArrayList<Usuario> {
        val listaUsuarios = ArrayList<Usuario>()
        val nombres = listOf("Gandalf", "Frodo", "Legolas", "Aragorn", "Sauron")

        for (i in 1..x) {
            val nombreElegido = nombres.random()
            val edad = Random.nextInt(10, 80)
            val usu = Usuario(nombreElegido, edad)
            listaUsuarios.add(usu)
        }

        return listaUsuarios
    }
}
