
package org.codeagle.elpozito.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.codeagle.elpozito.bd.ConexionMySQL;
import org.codeagle.elpozito.model.Empleado;
import org.codeagle.elpozito.model.Persona;
import org.codeagle.elpozito.model.Usuario;

public class ControllerEmpleado {
     public List<Empleado> getAll() {
    //Aqui va ña primera parte del paso 6
        List<Empleado> listaEmpleados = new ArrayList<>();
        try {
            //1) Crear la setencia SQL
            String query = "SELECT * FROM v_empleado";
            //2) Se establece la conexion con la base de datos
            ConexionMySQL connMySQL = new ConexionMySQL();
            //3) Se abre la conexion y devuelve un tipo conexion
            Connection conn = connMySQL.open();
            //4) Se genera el statement para enviar la consulta
            PreparedStatement pstm = conn.prepareStatement(query);
            //5.Se prepara un ResultSet para obtener la respuesta de la BD
            ResultSet rs = pstm.executeQuery();
            //6) Recorrer el rs y extraer los datos

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombreUsuario(rs.getString("Nombre_Usuario"));
                u.setContrasenia(rs.getString("contrasenia"));
                u.setRol(rs.getString("Rol"));
                u.setToken(rs.getString("Token"));
       
                Persona p = new Persona();
                p.setIdPersona(rs.getInt("idPersona"));
                p.setFoto(rs.getString("Foto_E"));
                p.setFechaNacimiento(rs.getString("Fecha_Nacimiento"));
                p.setTelefono(rs.getString("Telefono_E"));
                p.setDomicilio(rs.getString("Domicilio_E"));
                p.setCodigoPostal(rs.getString("CP"));
                p.setCiudad(rs.getString("Ciudad_E"));
                p.setEstado(rs.getString("Estado_E"));
                p.setNombre(rs.getString("Nombre_E"));
                p.setApellidoPaterno(rs.getString("Apellido_Paterno_E"));
                p.setApellidoMaterno(rs.getString("Apellido_Materno_E"));
                p.setGenero(rs.getString("Genero_E"));
                p.setCurp(rs.getString("CURP_E"));
                p.setRfc(rs.getString("RFC_E"));

                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setCodigo(rs.getString("Empleado_Codigo"));
                e.setFechaIngreso(rs.getString("Fecha_Ingreso"));
                e.setPuesto(rs.getString("Puesto"));
                e.setSalarioBruto(rs.getFloat("Salario_Bruto"));
                e.setActivo(rs.getInt("Estatus_E"));
                e.setEmail(rs.getString("Email_E"));

                e.setUsuario(u);
                e.setPersona(p);

                listaEmpleados.add(e);
            }
            //7) Cerrar todos los objetos
            rs.close();
            pstm.close();
            conn.close();
            connMySQL.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //8) Devolver la informacion
        return listaEmpleados;
    }

    public void delete(int idEmpleado) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE empleado SET activo=0 WHERE idEmpleado=" + idEmpleado + ";";
        //2.- Crear un objeto para la conexion con mySql
        ConexionMySQL conMySQL = new ConexionMySQL();
        //3. Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement para enviar la query
        Statement stmt = conn.createStatement();
        //5.- Ejecutar la sentencia
        stmt.execute(query);
        //6.- Cerrar los objetos
        stmt.close();
        conn.close();
        conMySQL.close();
    }
    
     public void reactivar(int idEmpleado) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE empleado SET activo=1 WHERE idEmpleado=" + idEmpleado + ";";
        //2.- Crear un objeto para la conexion con mySql
        ConexionMySQL conMySQL = new ConexionMySQL();
        //3. Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement para enviar la query
        Statement stmt = conn.createStatement();
        //5.- Ejecutar la sentencia
        stmt.execute(query);
        //6.- Cerrar los objetos
        stmt.close();
        conn.close();
        conMySQL.close();
    }
     

    public int insert(Empleado e) throws SQLException {
        //1.- Generar la sentencia SQL
        String query = "{call insertarEmpleado(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        //2.- Crear la conexion a la BD
        ConexionMySQL conMySQL = new ConexionMySQL();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta   prepareStatement, Statement y calleblestatement
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        cstm.setString(1, e.getPersona().getNombre());
        cstm.setString(2, e.getPersona().getApellidoPaterno());
        cstm.setString(3, e.getPersona().getApellidoMaterno());
        cstm.setString(4, e.getPersona().getGenero());
        cstm.setString(5, e.getPersona().getFechaNacimiento());
        cstm.setString(6, e.getPersona().getRfc());
        cstm.setString(7, e.getPersona().getCurp());
        cstm.setString(8, e.getPersona().getDomicilio());
        cstm.setString(9, e.getPersona().getCodigoPostal());
        cstm.setString(10, e.getPersona().getCiudad());
        cstm.setString(11, e.getPersona().getEstado());
        cstm.setString(12, e.getPersona().getTelefono());
        cstm.setString(13, e.getPersona().getFoto());
        cstm.setString(14, e.getUsuario().getNombreUsuario());
        cstm.setString(15, e.getUsuario().getContrasenia());

        cstm.setString(16, e.getUsuario().getRol());

        cstm.setString(17, e.getEmail());

        cstm.setString(18, e.getPuesto());

        cstm.setFloat(19, e.getSalarioBruto());

        cstm.registerOutParameter(20, Types.INTEGER);
        cstm.registerOutParameter(21, Types.INTEGER);
        cstm.registerOutParameter(22, Types.INTEGER);
        cstm.registerOutParameter(23, Types.VARCHAR);

        //6.- Ejecutar la sentencia
        cstm.execute();
        //7.- Obtener todos los parametros de retorno
        e.getPersona().setIdPersona(cstm.getInt(20));
        e.getUsuario().setIdUsuario(cstm.getInt(21));
        e.setIdEmpleado(cstm.getInt(22));
        e.setCodigo(cstm.getString(23));
        //8.- Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        return e.getIdEmpleado();
    }
    
     public int modificar(Empleado e) throws SQLException {
    // Generar la sentencia SQL
    String query = "{call modificarEmpleado(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    // Crear la conexión a la BD
    ConexionMySQL conMySQL = new ConexionMySQL();
    Connection conn = conMySQL.open();
    CallableStatement cstm = conn.prepareCall(query);

    // Llenar todos los parámetros de la llamada al procedimiento
    cstm.setString(1, e.getPersona().getNombre());
        cstm.setString(2, e.getPersona().getApellidoPaterno());
        cstm.setString(3, e.getPersona().getApellidoMaterno());
        cstm.setString(4, e.getPersona().getGenero());
        cstm.setString(5, e.getPersona().getFechaNacimiento());
        cstm.setString(6, e.getPersona().getRfc());
        cstm.setString(7, e.getPersona().getCurp());
        cstm.setString(8, e.getPersona().getDomicilio());
        cstm.setString(9, e.getPersona().getCodigoPostal());
        cstm.setString(10, e.getPersona().getCiudad());
        cstm.setString(11, e.getPersona().getEstado());
        cstm.setString(12, e.getPersona().getTelefono());
        cstm.setString(13, e.getPersona().getFoto());

        cstm.setString(14, e.getUsuario().getNombreUsuario());
        cstm.setString(15, e.getUsuario().getContrasenia());

        cstm.setString(16, e.getUsuario().getRol());

        cstm.setString(17, e.getEmail());

        cstm.setString(18, e.getPuesto());

        cstm.setFloat(19, e.getSalarioBruto());

        cstm.setInt(20, e.getPersona().getIdPersona());
        cstm.setInt(21, e.getUsuario().getIdUsuario());
        cstm.setInt(22, e.getIdEmpleado());
        cstm.setString(23, e.getCodigo());

  cstm.execute();
        // Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
    // Devolver el ID del empleado
        return e.getIdEmpleado();
}
   

}
