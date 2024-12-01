
package org.codeagle.elpozito.model;

/**
 *
 * @author maico
 */
public class Inventario {
   private int idInventario;
   private Producto producto;
  private int existencias;

    public Inventario() {
    }

    public Inventario(int idInventario, Producto producto, int existencias) {
        this.idInventario = idInventario;
        this.producto = producto;
        this.existencias = existencias;
    }

    public Inventario(Producto producto, int existencias) {
        this.producto = producto;
        this.existencias = existencias;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventario{");
        sb.append("idInventario=").append(idInventario);
        sb.append(", producto=").append(producto);
        sb.append(", existencias=").append(existencias);
        sb.append('}');
        return sb.toString();
    }
  
}
