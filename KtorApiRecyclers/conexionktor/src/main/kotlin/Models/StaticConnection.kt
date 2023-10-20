package Models

import java.sql.*
object StaticConnection {
    var conexion: Connection? = null
    var sentenciaSQL: Statement? = null
    var registros: ResultSet? = null
    fun abrirConexion(): Int {
        var cod = 0
        try {
            val controlador = "com.mysql.cj.jdbc.Driver"
            val URL_BD = "jdbc:mysql://" + Constants.servidor+":"+Constants.puertoDB+"/" + Constants.bbdd
            Class.forName(controlador)
            conexion = DriverManager.getConnection(URL_BD, Constants.dbUser, Constants.dbPwd)
            sentenciaSQL = StaticConnection.conexion!!.createStatement()
            println("Conexion realizada con éxito")
        } catch (e: Exception) {
            System.err.println("Exception: " + e.message)
            cod = -1
        }
        return cod
    }

    // ------------------------------------------------------
    fun cerrarConexion(): Int {
        var cod = 0
        try {
            conexion!!.close()
            println("Desconectado de la Base de Datos") // Opcional para seguridad
        } catch (ex: SQLException) {
            cod = -1
        }
        return cod
    }

    fun obtenerTodosLosUsuarios():ArrayList<User>{
        val users = arrayListOf<User>()
        try{
            var res:Int= abrirConexion()
            println(res)
            registros = sentenciaSQL!!.executeQuery("select * from users")
            while(registros!!.next()){
                users.add(
                    User(
                        registros!!.getInt(1),
                        registros!!.getString(2),
                        registros!!.getString(3),
                        registros!!.getString(4),
                        registros!!.getBoolean(5)
                    )
                )
            }
            cerrarConexion()
        }catch(ex: Exception){
            println(ex.message)
        }
        return users
    }
}