package org.codeagle.elpozito.model;

public class DetalleVenta {
    
    private Venta venta;
    private Producto producto;
    private float precioVenta;
    private int cantidad;

    public DetalleVenta() {
    }

    public DetalleVenta(Venta venta, Producto producto, float precioVenta, int cantidad) {
        this.venta = venta;
        this.producto = producto;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleVenta{");
        sb.append("venta:").append(venta);
        sb.append(", producto:").append(producto);
        sb.append(", precioVenta:").append(precioVenta);
        sb.append(", cantidad:").append(cantidad);
        sb.append('}');
        return sb.toString();
    }
 
}

