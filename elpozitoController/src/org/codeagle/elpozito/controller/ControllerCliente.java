
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
import org.codeagle.elpozito.model.Cliente;
import org.codeagle.elpozito.model.Persona;



public class ControllerCliente {
     public List<Cliente> getAllCliente() {
    //Aqui va ña primera parte del paso 6
        List<Cliente> listaCliente = new ArrayList<>();
        try {
            //1) Crear la setencia SQL
            String query = "SELECT * FROM v_clientes";
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

                Persona p = new Persona();
                p.setIdPersona(rs.getInt("idPersona"));
                p.setFoto(rs.getString("Foto_C"));
                p.setFechaNacimiento(rs.getString("Fecha_Nacimiento"));
                p.setTelefono(rs.getString("Telefono_C"));
                p.setDomicilio(rs.getString("Domicilio_C"));
                p.setCodigoPostal(rs.getString("CP"));
                p.setCiudad(rs.getString("Ciudad_C"));
                p.setEstado(rs.getString("Estado_C"));
                p.setNombre(rs.getString("Nombre_C"));
                p.setApellidoPaterno(rs.getString("Apellido_Paterno_C"));
                p.setApellidoMaterno(rs.getString("Apellido_Materno_C"));
                p.setGenero(rs.getString("Genero_C"));
                p.setCurp(rs.getString("CURP_C"));
                p.setRfc(rs.getString("RFC_C"));

                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setEmail(rs.getString("Email_C"));
                c.setFechaRegistro(rs.getString("Fecha_Registro"));
                c.setEstatus(rs.getInt("Estatus_C"));
                c.setPersona(p);

                listaCliente.add(c);
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
        return listaCliente;
    }

    public void delete(int idCliente) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE cliente SET estatus=0 WHERE idCliente=" + idCliente + ";";
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
    
     public void reactivar(int idCliente) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE cliente SET estatus=1 WHERE idCliente=" + idCliente + ";";
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
     

    public int insert(Cliente c) throws SQLException {
        //1.- Generar la sentencia SQL
        String query = "{call insertarCliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        //2.- Crear la conexion a la BD
        ConexionMySQL conMySQL = new ConexionMySQL();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta   prepareStatement, Statement y calleblestatement
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        cstm.setString(1, c.getPersona().getNombre());
        cstm.setString(2, c.getPersona().getApellidoPaterno());
        cstm.setString(3, c.getPersona().getApellidoMaterno());
        cstm.setString(4, c.getPersona().getGenero());
        cstm.setString(5, c.getPersona().getFechaNacimiento());
        cstm.setString(6, c.getPersona().getRfc());
        cstm.setString(7, c.getPersona().getCurp());
        cstm.setString(8, c.getPersona().getDomicilio());
        cstm.setString(9, c.getPersona().getCodigoPostal());
        cstm.setString(10, c.getPersona().getCiudad());
        cstm.setString(11, c.getPersona().getEstado());
        cstm.setString(12, c.getPersona().getTelefono());
        cstm.setString(13, c.getPersona().getFoto());
        
        cstm.setString(14, c.getEmail());

        cstm.registerOutParameter(15, Types.INTEGER);
        cstm.registerOutParameter(16, Types.INTEGER);

        //6.- Ejecutar la sentencia
        cstm.execute();
        //7.- Obtener todos los parametros de retorno
        c.getPersona().setIdPersona(cstm.getInt(15));
        c.setIdCliente(cstm.getInt(16));
        //8.- Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        return c.getIdCliente();
    }
    
    public int modificar(Cliente c) throws SQLException {
    // Generar la sentencia SQL
    String query = "{call modificarCliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    // Crear la conexión a la BD
    ConexionMySQL conMySQL = new ConexionMySQL();
    Connection conn = conMySQL.open();
    CallableStatement cstm = conn.prepareCall(query);

    // Llenar todos los parámetros de la llamada al procedimiento
    cstm.setString(1, c.getPersona().getNombre());
        cstm.setString(2, c.getPersona().getApellidoPaterno());
        cstm.setString(3, c.getPersona().getApellidoMaterno());
        cstm.setString(4, c.getPersona().getGenero());
        cstm.setString(5, c.getPersona().getFechaNacimiento());
        cstm.setString(6, c.getPersona().getRfc());
        cstm.setString(7, c.getPersona().getCurp());
        cstm.setString(8, c.getPersona().getDomicilio());
        cstm.setString(9, c.getPersona().getCodigoPostal());
        cstm.setString(10, c.getPersona().getCiudad());
        cstm.setString(11, c.getPersona().getEstado());
        cstm.setString(12, c.getPersona().getTelefono());
        cstm.setString(13, c.getPersona().getFoto());
        
        cstm.setString(14, c.getEmail());

        cstm.setInt(15, c.getPersona().getIdPersona());
        cstm.setInt(16, c.getIdCliente());

  cstm.execute();
        // Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
    // Devolver el ID del empleado
        return c.getIdCliente();
}
    
  public ArrayList<Cliente> search(String param) throws SQLException {
//Aqui va la primera parte del paso 6
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        try {
            //1) Crear la setencia SQL
            String query = "SELECT c.*, p.* FROM cliente c " +
               "JOIN persona p ON c.idPersona = p.idPersona " +
               "WHERE " +
               "c.idCliente LIKE ? OR " +
               "p.nombre LIKE ? OR " +
               "p.apellidoPaterno LIKE ? OR " +
               "p.apellidoMaterno LIKE ? OR " +
               "p.telefono LIKE ?;";
            //2) Se establece la conexion con la base de datos
            ConexionMySQL connMySQL = new ConexionMySQL();
            //3) Se abre la conexion y devuelve un tipo conexion
            Connection conn = connMySQL.open();
            //4) Se genera el statement para enviar la consulta
            PreparedStatement pstm = conn.prepareStatement(query);
            for (int i = 1; i <= 5; i++) {
            pstm.setString(i, "%" + param + "%");
            }
            //5.Se prepara un ResultSet para obtener la respuesta de la BD
            ResultSet rs = pstm.executeQuery();
            //6) Recorrer el rs y extraer los datos

            listaCliente = listClientes(rs);
            //7) Cerrar todos los objetos
            rs.close();
            pstm.close();
            conn.close();
            connMySQL.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //8) Devolver la informacion
        return listaCliente;
    }
    
    public ArrayList<Cliente> listClientes(ResultSet rs) throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        while (rs.next()) {
            Persona persona = new Persona(rs.getInt("idPersona"), rs.getString("Nombre"), rs.getString("apellidoPaterno"), rs.getString("apellidoMaterno"), rs.getString("genero"),
                    rs.getString("fechaNacimiento"), rs.getString("rfc"), rs.getString("curp"), rs.getString("domicilio"), rs.getString("codigoPostal"), rs.getString("ciudad"),
                    rs.getString("estado"), rs.getString("telefono"), rs.getString("foto"));
            Cliente cliente = new Cliente(rs.getInt("idCliente"), rs.getString("fechaRegistro"), rs.getInt("estatus"), rs.getString("email"), persona);
            
            clientes.add(cliente);
        }
        return clientes;
    }   
    
    
}
