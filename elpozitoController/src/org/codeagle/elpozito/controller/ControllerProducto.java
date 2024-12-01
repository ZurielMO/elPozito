package org.codeagle.elpozito.controller;
/**
 *
 * @author maico
 */
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
import org.codeagle.elpozito.model.Inventario;
import org.codeagle.elpozito.model.Producto;
import org.codeagle.elpozito.model.Usuario;

public class ControllerProducto {
     //------------------------------------------------------------------------------------------------------
    ControllerAcceso ca=new ControllerAcceso();
    Usuario u=new Usuario();
    public List<Producto> getAllProducto() {
    //Aqui va la primera parte del paso 6
        List<Producto> listaProducto = new ArrayList<>();
        try {
            //1) Crear la setencia SQL
            String query = "SELECT * FROM v_producto";
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
                Producto p = new Producto();
                p.setCodigoBarras(rs.getString("Codigo_Barras"));
                p.setEstatus(rs.getInt("Estatus"));
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("Nombre_Producto"));
                p.setPrecioCompra(rs.getFloat("Precio_Compra"));
                p.setPrecioVenta(rs.getFloat("Precio_Venta"));
                p.setPresentacion(rs.getString("Presentacion"));
                p.setUnidadMedida(rs.getString("Unidad_Medida"));
                p.setCategoria(rs.getString("Categoria"));
                p.setCodigoProducto(rs.getString("Codigo_Producto"));
                
           
                listaProducto.add(p);
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
        return listaProducto;
    }
    //------------------------------------------------------------------------------------------------------
    public void delete(int idProducto) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE producto SET estatus=0 WHERE idProducto=" + idProducto + ";";
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
    //------------------------------------------------------------------------------------------------------
     public void reactivar(int idProducto) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE producto SET estatus=1 WHERE idProducto=" + idProducto + ";";
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
    //------------------------------------------------------------------------------------------------------
    
    public int insert(Producto p) throws SQLException {
//        if(ca.authToken(u.getToken())){
        //1.- Generar la sentencia SQL
        String query = "{call insertarProducto(?,?,?,?,?,?,?,?,?)}";
        //2.- Crear la conexion a la BD
        ConexionMySQL conMySQL = new ConexionMySQL();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta   prepareStatement, Statement y calleblestatement
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        cstm.setString(1, p.getNombre());
        cstm.setString(2, p.getUnidadMedida());
        cstm.setString(3, p.getPresentacion());
        cstm.setString(4, p.getCategoria());
        cstm.setFloat(5, p.getPrecioCompra());
        cstm.setFloat(6, p.getPrecioVenta());
        cstm.setString(7, p.getCodigoProducto());
        cstm.setString(8, p.getCodigoBarras());

        cstm.registerOutParameter(9, Types.INTEGER);

        //6.- Ejecutar la sentencia
        cstm.execute();
        //7.- Obtener todos los parametros de retorno
        p.setIdProducto(cstm.getInt(9));
        //8.- Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        return p.getIdProducto();
//        }
//        else{
//            return 0;
//        }
    }
    //------------------------------------------------------------------------------------------------------
    public int update(Producto p) throws SQLException {
        //1.- Generar la sentencia SQL
        String query = "{call modificarProducto(?,?,?,?,?,?,?,?,?)}";
        //2.- Crear la conexion a la BD
        ConexionMySQL conMySQL = new ConexionMySQL();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta   prepareStatement, Statement y calleblestatement
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        cstm.setString(1, p.getNombre());
        cstm.setString(2, p.getUnidadMedida());
        cstm.setString(3, p.getPresentacion());
        cstm.setString(4, p.getCategoria());
        cstm.setFloat(5, p.getPrecioCompra());
        cstm.setFloat(6, p.getPrecioVenta());
        cstm.setString(7, p.getCodigoProducto());
        cstm.setString(8, p.getCodigoBarras());

        cstm.setInt(9, p.getIdProducto());
        

        //6.- Ejecutar la sentencia
        cstm.execute();
        //7.- Obtener todos los parametros de retorno

        //8.- Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        return p.getIdProducto();
    }
    //------------------------------------------------------------------------------------------------------
    
    
    public ArrayList<Producto> search(String parametro) throws SQLException {
    ArrayList<Producto> listaProducto = new ArrayList<>();
    try {
        // 1) Crear la sentencia SQL
        String query = "SELECT * FROM producto WHERE " +
                       "nombre LIKE ? OR " +
                       "categoria LIKE ? OR " +
                       "codigoProducto LIKE ? OR " +
                       "codigoBarras LIKE ?;";
        // 2) Se establece la conexión con la base de datos
        ConexionMySQL connMySQL = new ConexionMySQL();
        // 3) Se abre la conexión y devuelve un tipo de conexión
        Connection conn = connMySQL.open();
        // 4) Se genera el statement para enviar la consulta
        PreparedStatement pstm = conn.prepareStatement(query);
        for(int i = 1; i <= 4; i++) {
            pstm.setString(i, "%" + parametro + "%");
        }
        // 5. Se prepara un ResultSet para obtener la respuesta de la BD
        ResultSet rs = pstm.executeQuery();
        // 6) Recorrer el rs y extraer los datos
        listaProducto = listProductos(rs);
        // 7) Cerrar todos los objetos
        rs.close();
        pstm.close();
        conn.close();
        connMySQL.close();

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    // 8) Devolver la información
    return listaProducto;
}

public ArrayList<Producto> listProductos(ResultSet rs) throws SQLException {
    ArrayList<Producto> productos = new ArrayList<>();
    while (rs.next()) {
        Producto prod = new Producto(
            rs.getInt("idProducto"),
            rs.getString("nombre"),
            rs.getString("unidadMedida"),
            rs.getString("presentacion"),
            rs.getString("categoria"),
            rs.getFloat("precioCompra"),
            rs.getFloat("precioVenta"),
            rs.getString("codigoProducto"),
            rs.getString("codigoBarras"),
            rs.getInt("estatus")          
        );
        productos.add(prod);
    }
    return productos;
}
    
    
    
   public ArrayList<Inventario> searchI(String parametro) throws SQLException {
    ArrayList<Inventario> listaInventario = new ArrayList<>();
    try {
        // Se crea la setencia SQL
        String query = "SELECT * FROM vista_inventario WHERE " +
                        "idProducto LIKE ?;";

        // Se establece la conexión con la base de datos
        ConexionMySQL connMySQL = new ConexionMySQL();
        // Se abre la conexión y devuelve un tipo Connection
        Connection conn = connMySQL.open();
        // Se genera el statement para enviar la consulta
        PreparedStatement pstm = conn.prepareStatement(query);
        for(int i = 1; i <= 1; i++){
            pstm.setString(i, "%" + parametro + "%");
        }
        // Se prepara un ResultSet para obtener la respuesta de la BD
        ResultSet rs = pstm.executeQuery();
        // Se llama al método para obtener la lista de inventarios
        listaInventario = listInventario(rs);

        // Se cierran todos los objetos
        rs.close();
        pstm.close();
        conn.close();
        connMySQL.close();

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    // Se devuelve la información
    return listaInventario;
}

public ArrayList<Inventario> listInventario(ResultSet rs) throws SQLException {
    ArrayList<Inventario> inventarios = new ArrayList<>();
    while (rs.next()) {
        // Crear un nuevo objeto Producto con los datos de la fila actual
        Producto producto = new Producto(
            rs.getInt("idProducto"),
            rs.getString("nombre_producto"),
            rs.getString("unidadMedida"),
            rs.getString("presentacion"),
            rs.getString("categoria"),
            rs.getFloat("precioCompra"),
            rs.getFloat("precioVenta"),
            rs.getString("codigoProducto"),
            rs.getString("codigoBarras"),
            rs.getInt("estatus_producto")
        );

        // Crear un nuevo objeto Inventario con los datos de la fila actual
        Inventario inv = new Inventario(
            rs.getInt("idInventario"),
            producto,
            rs.getInt("existencias")
        );
        inventarios.add(inv);
    }
    return inventarios;
}
    
    
}

