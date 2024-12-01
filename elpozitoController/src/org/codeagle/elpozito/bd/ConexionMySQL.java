package org.codeagle.elpozito.bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author maico
 */
public class ConexionMySQL {
  Connection conn;
 public Connection open(){
     try {
         String u="root";
         String c="Maico";
         String r="jdbc:mysql://localhost:3306/pozito";
         
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn=DriverManager.getConnection(r, u, c);
     } catch (ClassNotFoundException ex) {
ex.printStackTrace();
     } catch (SQLException ex) {
ex.printStackTrace();
     }
    return conn;
 }
  public void close(){
      if (conn!=null) {
          try {
              conn.close();
          } catch (SQLException ex) {
ex.printStackTrace();
          }
      }
  }   
    
}
