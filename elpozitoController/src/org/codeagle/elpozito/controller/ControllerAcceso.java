package org.codeagle.elpozito.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.codeagle.elpozito.bd.ConexionMySQL;
import org.codeagle.elpozito.model.Empleado;
import org.codeagle.elpozito.model.Usuario;


/**
 *
 * @author maico
 */
public class ControllerAcceso {
    public void login(Usuario u) throws SQLException{
       String query = """
                      SELECT idUsuario FROM usuario WHERE nombreUsuario="%S" && contrasenia="%S";
                      """;
       query = String.format(query, u.getNombreUsuario(), u.getContrasenia());
       
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn= connMySQL.open();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        if (rs.next()) {
         u.setIdUsuario(rs.getInt("idUsuario")); 
        }
        rs.close();
        stmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public void saveToken (Usuario u) throws SQLException
    {
String query="""
             UPDATE usuario SET token="%S" WHERE idUsuario=%S;
             """;
query = String.format(query, u.getToken(), u.getIdUsuario());
ConexionMySQL conMySQL = new ConexionMySQL();
Connection conn= conMySQL.open();
Statement stmt = conn.createStatement();
stmt.execute(query);
stmt.close();
conn.close();
conMySQL.close();

    }
    
    
    public void deleteToken (String token) throws SQLException
    {
String query="""
             UPDATE usuario SET token="" WHERE token="%S";
             """;
query = String.format(query,token);
ConexionMySQL conMySQL = new ConexionMySQL();
Connection conn= conMySQL.open();
Statement stmt = conn.createStatement();
stmt.execute(query);
stmt.close();
conn.close();
conMySQL.close();

    }
    
    
    public boolean authToken(String token) throws SQLException{
        boolean result=false;
String query="SELECT * FROM usuario WHERE token=?";
ConexionMySQL conMySQL = new ConexionMySQL();
Connection conn= conMySQL.open();
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, token);
ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result=true;
        }
rs.close();
pstmt.close();
conn.close();
conMySQL.close();
return result;
    }
    
    
 public List<Usuario> getAllUsuario(){
      List<Usuario> listUsuario = new ArrayList<>();   
     try {
            String query = "SELECT * FROM usuario";
            ConexionMySQL conMySQL = new ConexionMySQL();
            Connection conn = conMySQL.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                Usuario u = new Usuario();
                u.setContrasenia(rs.getString("contrasenia"));
                u.setNombreUsuario(rs.getString("nombreUsuario"));
                u.setIdUsuario(rs.getInt("idUsuario"));
                listUsuario.add(u);
            }
              
              rs.close();
              pstm.close();
              conn.close();
              conMySQL.close();
            
        } catch (SQLException ex) {
ex.printStackTrace();
        }
     return listUsuario;
 }

  public void indetificarEmp (Usuario u, Empleado e) throws SQLException
    {
String query="""
             SELECT idEmpleado FROM empleado WHERE idUsuario=%d;
             """;
query = String.format(query,u.getIdUsuario());
ConexionMySQL conMySQL = new ConexionMySQL();
Connection conn= conMySQL.open();
Statement stmt = conn.createStatement();
ResultSet rs= stmt.executeQuery(query);
if(rs.next()){
    e.setIdEmpleado(rs.getInt("idEmpleado"));
}
rs.close();
stmt.close();
conn.close();
conMySQL.close();

    }
 
}
