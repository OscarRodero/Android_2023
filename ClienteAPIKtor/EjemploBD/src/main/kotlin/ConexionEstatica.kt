import java.sql.*
import java.util.*


object ConexionEstatica {
    // ********************* Atributos *************************
    var conexion: Connection? = null

    // Atributo a través del cual hacemos la conexión física.
    var sentenciaSQL: Statement? = null

    // Atributo que nos permite ejecutar una sentencia SQL
    var registros: ResultSet? = null

    // ----------------------------------------------------------
    fun abrirConexion(): Int {
        var cod = 0
        try {
            //Otros posibles controladores.
            // String controlador = "oracle.jdbc.driver.OracleDriver"
            // String controlador = "sun.jdbc.odbc.JdbcOdbcDriver"
            // String URL_BD = "jdbc:oracle:oci:@REPASO";
            // String URL_BD = "jdbc:oracle:oci:@REPASO";
            // String URL_BD = "jdbc:odbc:REPASO";

            // Cargar el driver/controlador JDBC de MySql
            val controlador = "com.mysql.cj.jdbc.Driver"
            val URL_BD = "jdbc:mysql://" + Constantes.servidor+":"+Constantes.puerto+"/" + Constantes.bbdd

            // Cargar el driver/controlador MariaSQL
//            val controlador = "org.mariadb.jdbc.Driver" // MariaDB la version libre de MySQL (requiere incluir la librería jar correspondiente).
//            val URL_BD = "jdbc:mariadb://" + Constantes.servidor+":"+Constantes.puerto+"/"+Constantes.bbdd+""

            Class.forName(controlador)

            // Realizamos la conexión a una BD con un usuario y una clave.
            conexion = DriverManager.getConnection(URL_BD, Constantes.usuario, Constantes.passwd)
            sentenciaSQL = ConexionEstatica.conexion!!.createStatement()
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

    // ---------------------------------------------------------
    fun login(nombre: String?, clave: String?): Persona? {
        var p: Persona? = null
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaPersonas + " WHERE nombre = ? AND clave = ?"
            val pstmt = conexion!!.prepareStatement(sentencia)
            // pstmt.setInt(1, 30000);
            pstmt.setString(1, nombre)
            pstmt.setString(2, clave)
            registros = pstmt.executeQuery()
            while (ConexionEstatica.registros!!.next()) {
                p = Persona(
                    ConexionEstatica.registros!!.getString("dni"), ConexionEstatica.registros!!.getString("nombre"), ConexionEstatica.registros!!.getString("tfno"),
                    ConexionEstatica.registros!!.getString("clave")
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return p
    }

    // ----------------------------------------------------------
    fun obtenerPersonasArrayList(): ArrayList<Persona> {
        val lp: ArrayList<Persona> = ArrayList(1)
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaPersonas
            registros = sentenciaSQL!!.executeQuery(sentencia)
            while (ConexionEstatica.registros!!.next()) {
                lp.add(
                    Persona(
                        ConexionEstatica.registros!!.getString("dni"),
                        ConexionEstatica.registros!!.getString("nombre"),
                        ConexionEstatica.registros!!.getString("tfno"),
                        ConexionEstatica.registros!!.getString("clave")
                    )
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return lp
    }

    // ----------------------------------------------------------
    fun modificarPersona(dni: String, nuevonombre: String): Int {
        var cuantos = 0
        try {
            abrirConexion()
            val sentencia = ("UPDATE " + Constantes.TablaPersonas + " SET nombre = '" + nuevonombre
                    + "' WHERE dni = '" + dni + "'")
            cuantos = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cuantos
    }

    // ----------------------------------------------------------
    fun modificarPersonaBind(dni: String?, nuevonombre: String?): Int {
        var cuantos = 0
        try {
            abrirConexion()
            val sentencia = "UPDATE " + Constantes.TablaPersonas + " SET nombre = ? WHERE dni = ?"
            val pstmt = conexion!!.prepareStatement(sentencia)
            pstmt.setString(1, nuevonombre)
            pstmt.setString(2, dni)
            cuantos = pstmt.executeUpdate()
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cuantos
    }

    // ----------------------------------------------------------
    fun insertarPersona(dni: String, nombre: String, clave: String, tfno: String): Int {
        var cod = 0
        val sentencia = ("INSERT INTO " + Constantes.TablaPersonas + " VALUES ('" + dni + "'," + "'" + nombre + "','"
                + clave + "','" + tfno + "')")
        try {
            abrirConexion()
            sentenciaSQL!!.executeUpdate(sentencia)
        } catch (sq: SQLException) {
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }
        return cod
    }

    // ----------------------------------------------------------
    fun insertarPersonaBind(dni: String?, nombre: String?, clave: String?, tfno: String?): Int {
        var cod = 0
        val sentencia = "INSERT INTO " + Constantes.TablaPersonas + " VALUES (?, ?, ?, ?)"
        try {
            abrirConexion()
            val pstmt = conexion!!.prepareStatement(sentencia)
            pstmt.setString(1, dni)
            pstmt.setString(2, nombre)
            pstmt.setString(3, clave)
            pstmt.setString(4, tfno)
            pstmt.executeUpdate()
        } catch (sq: SQLException) {
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }
        return cod
    }

    // ----------------------------------------------------------
    fun borrarPersona(dni: String): Int {
        var cuantos = 0
        val sentencia = "DELETE FROM " + Constantes.TablaPersonas + " WHERE dni = '" + dni + "'"
        try {
            abrirConexion()
            cuantos = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cuantos
    }

    // ----------------------------------------------------------
    fun borrarPersonaBind(dni: String?): Int {
        var cuantos = 0
        val sentencia = "DELETE FROM " + Constantes.TablaPersonas + " WHERE dni = ?"
        try {
            abrirConexion()
            val pstmt = conexion!!.prepareStatement(sentencia)
            pstmt.setString(1, dni)
            cuantos = pstmt.executeUpdate()
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cuantos
    }

    // ------------------------------------------------------
    fun borrarPersonas(): Int {
        val sentencia = "TRUNCATE " + Constantes.TablaPersonas
        var cod = 0
        try {
            abrirConexion()
            sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
            cod = ex.errorCode
        } finally {
            cerrarConexion()
        }
        return cod
    }

    // ********************** Métodos para manejar el cursor desde fuera sin usar
    // realmente el cursor **************************
    // ----------------------------------------------------------
    // ********************** Métodos para manejar el cursor desde fuera sin usar
    // realmente el cursor **************************
    // ----------------------------------------------------------
    /******
     * Este método tiene un abri conexión pero no un cerrar conexión porque me
     * servirá para llenar
     * el cursor y usarlo con los métodos posteriores.
     */
    fun rellenarDatosCursor(): Int {
        var cod = 0
        val sentencia = "SELECT * FROM " + Constantes.TablaPersonas
        try {
            registros = sentenciaSQL!!.executeQuery(sentencia)
        } catch (ex: SQLException) {
            cod = ex.errorCode
        }
        return cod
    }

    // ----------------------------------------------------------
    fun getRegistroActual(): Persona? {
        var p: Persona? = null
        try {
            // Num_Cols = registros.getMetaData().getColumnCount();
            p = Persona(
                registros!!.getString("dni"), registros!!.getString("nombre"), registros!!.getString("tfno"),
                registros!!.getString("clave")
            )
        } catch (ex: SQLException) {
        }
        return p
    }

    // ------------------------------------------------------
    fun obtenerPrimero(Campo: String?): Persona? {
        var p: Persona? = null
        try {
            if (registros!!.first()) {
                p = Persona(
                    registros!!.getString("dni"), registros!!.getString("nombre"), registros!!.getString("tfno"),
                    registros!!.getString("clave")
                )
            }
            // valor = Conj_Registros.getString(Campo);
        } catch (ex: SQLException) {
        }
        return p
    }

    // ------------------------------------------------------
    fun obtenerUltimo(Campo: String?): Persona? {
        var p: Persona? = null
        try {
            if (registros!!.last()) {
                p = Persona(
                    registros!!.getString("dni"), registros!!.getString("nombre"), registros!!.getString("tfno"),
                    registros!!.getString("clave")
                )
            }
            // valor = Conj_Registros.getString(Campo);
        } catch (ex: SQLException) {
        }
        return p
    }

    // ------------------------------------------------------
    fun irSiguiente(): Boolean {
        var conseguido = false
        try {
            conseguido = registros!!.next()
        } catch (ex: SQLException) {
        }
        return conseguido
    }

    // ------------------------------------------------------
    fun irAnterior(): Boolean {
        var conseguido = false
        try {
            conseguido = registros!!.previous()
        } catch (ex: SQLException) {
        }
        return conseguido
    }
}