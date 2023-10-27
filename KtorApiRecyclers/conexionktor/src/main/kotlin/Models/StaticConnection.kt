package Models

import java.sql.*

object StaticConnection {
    var conexion: Connection? = null
    var sentenciaSQL: Statement? = null
    var registros: ResultSet? = null
    fun openConnection(): Int {
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
    fun closeConnection(): Int {
        var cod = 0
        try {
            conexion!!.close()
            println("Desconectado de la Base de Datos") // Opcional para seguridad
        } catch (ex: SQLException) {
            cod = -1
        }
        return cod
    }

    fun obtainAllUsers():ArrayList<User>{
        val users = arrayListOf<User>()
        try{
            var res:Int= openConnection()
            println(res)
            registros = sentenciaSQL!!.executeQuery("select * from users")
            while(registros!!.next()){
                users.add(
                    User(
                        registros!!.getString(2),
                        registros!!.getString(3),
                        registros!!.getString(4),
                        registros!!.getBoolean(5)
                    )
                )
            }
            closeConnection()
        }catch(ex: Exception){
            print(ex)
        }
        return users
    }

    fun login(usu: AuxUser): User? {
        val users = mutableListOf<User>()
        var miUsu: User? = null
        try {
            openConnection()
            val query = "SELECT * FROM users WHERE Email = ? AND Password = ?"
            val preparedStatement: PreparedStatement = conexion!!.prepareStatement(query)
            preparedStatement.setString(1, usu.Email)
            preparedStatement.setString(2, usu.Password)
            val registros = preparedStatement.executeQuery()
            while (registros.next()) {
                users.add(
                    User(
                        registros.getString(2),
                        registros.getString(3),
                        registros.getString(4),
                        registros.getBoolean(5)
                    )
                )
            }
            if (users.isNotEmpty()) {
                miUsu = users[0] // Tomar el primer usuario encontrado
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            closeConnection()
        }
        return miUsu
    }

    fun deleteUser(user: User): Int {
        var registrosBorrados = 0

        try {
            openConnection()
            val query = "DELETE FROM users WHERE Email = ?"
            val preparedStatement: PreparedStatement = conexion!!.prepareStatement(query)
            preparedStatement.setString(1, user.Email)
            registrosBorrados = preparedStatement.executeUpdate()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            closeConnection()
        }
        return registrosBorrados
    }

    fun register(usu: User): User? {
        var miUsu: User? = null
        try {
            openConnection()
            val query = "INSERT INTO Users (Username, Email, Password, IsAdmin) VALUES (?, ?, ?, ?);"
            val preparedStatement: PreparedStatement = conexion!!.prepareStatement(query)
            preparedStatement.setString(1, usu.Username)
            preparedStatement.setString(2, usu.Email)
            preparedStatement.setString(3, usu.Password)
            preparedStatement.setBoolean(4, usu.IsAdmin)

            val registros = preparedStatement.executeUpdate() // Ejecutar la inserción

            if (registros > 0) {
                // La inserción tuvo éxito, por lo que devolvemos el usuario que se acaba de insertar
                miUsu = usu
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            closeConnection()
        }
        return miUsu
    }



}