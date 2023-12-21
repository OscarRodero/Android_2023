fun main(args: Array<String>) {
    var lista: ArrayList<Persona> = ArrayList(1)
    var cod: Int
    var p: Persona?

    cod = ConexionEstatica.insertarPersona("200RE", "Alvaro", "1234", "555425423")
    if (cod != 0) {
        println("Se ha producido el error ${cod} al insertar: clave duplicada.")
    } else {
        println("Registro insertado")
    }

    cod = ConexionEstatica.insertarPersonaBind("90TE", "Marco", "1234", "5558592")
    if (cod != 0) {
        println("Se ha producido el error ${cod} al insertar: clave duplicada.")
    } else {
        println("Registro insertado")
    }

    cod = ConexionEstatica.modificarPersona("10A", "DAW1")
    if (cod != 0){
        println("Se ha producido el error ${cod} al modificar.")
    }
    else {
        println("Registro modificado")
    }
    cod = ConexionEstatica.modificarPersonaBind("10A", "DAW2")
    if (cod != 0){
        println("Se ha producido el error ${cod} al modificar.")
    }
    else {
        println("Registro modificado")
    }

    cod = ConexionEstatica.borrarPersona("11B")
    if (cod != 0){
        println("Se ha producido el error ${cod} al borrar.")
    }
    else {
        println("Registro borrado")
    }
    cod = ConexionEstatica.borrarPersonaBind("12J")
    if (cod != 0){
        println("Se ha producido el error ${cod} al borrar.")
    }
    else {
        println("Registro borrado")
    }

    println("*****************************************")
    lista = ConexionEstatica.obtenerPersonasArrayList()

    for (pe in  lista) {
        println(pe)
    }

    println("*****************************************")

    p = ConexionEstatica.login("Ramón", "1234")
    if (p == null) {
        println("Login incorrecto.")
    } else {
        println("Bienvenid@: ${p}")
    }

    // ************** Usando un cursor con los métodos proporcionados
    println("*********************************************************")
    println("Usando el cursor")
    if (ConexionEstatica.abrirConexion() == 0) {
        cod = ConexionEstatica.rellenarDatosCursor()
        if (cod == 0) {
            while (ConexionEstatica.irSiguiente()) {
                p = ConexionEstatica.getRegistroActual()
                println(p);
            }
        }
        ConexionEstatica.cerrarConexion();
    }
}