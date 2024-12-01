package org.codeagle.elpozito.model;

public class Producto {
    private int idProducto;
    private String nombre;
    private String unidadMedida;
    private String presentacion;
    private String categoria;
    private float precioCompra;
    private float precioVenta;
    private String codigoProducto;
    private String codigoBarras;
    private int estatus;

    public Producto() {
    }

    public Producto(String nombre, String unidadMedida, String presentacion, String categoria, float precioCompra, float precioVenta, String codigoProducto, String codigoBarras, int estatus) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.presentacion = presentacion;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.codigoProducto = codigoProducto;
        this.codigoBarras = codigoBarras;
        this.estatus = estatus;
    }

    public Producto(int idProducto, String nombre, String unidadMedida, String presentacion, String categoria, float precioCompra, float precioVenta, String codigoProducto, String codigoBarras, int estatus) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.presentacion = presentacion;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.codigoProducto = codigoProducto;
        this.codigoBarras = codigoBarras;
        this.estatus = estatus;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto{");
        sb.append("idProducto:").append(idProducto);
        sb.append(", nombre:").append(nombre);
        sb.append(", unidadMedida:").append(unidadMedida);
        sb.append(", presentacion:").append(presentacion);
        sb.append(", categoria:").append(categoria);
        sb.append(", precioCompra:").append(precioCompra);
        sb.append(", precioVenta:").append(precioVenta);
        sb.append(", codigoProducto:").append(codigoProducto);
        sb.append(", codigoBarras:").append(codigoBarras);
        sb.append(", estatus:").append(estatus);
        sb.append('}');
        return sb.toString();
    }
}