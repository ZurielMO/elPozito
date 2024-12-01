package org.codeagle.elpozito.model;

import java.util.List;

public class Venta {
    private int idventa;
    private String fechaHora;
    private Cliente cliente;
    private Empleado empleado;
    private int estatus;
    private List<DetalleVenta> listDV;

    public Venta(int idventa, String fechaHora, Cliente cliente, Empleado empleado, int estatus, List<DetalleVenta> listDV) {
        this.idventa = idventa;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.empleado = empleado;
        this.estatus = estatus;
        this.listDV = listDV;
    }

    public Venta() {
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public List<DetalleVenta> getListDV() {
        return listDV;
    }

    public void setListDV(List<DetalleVenta> listDV) {
        this.listDV = listDV;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venta{");
        sb.append("idventa:").append(idventa);
        sb.append(", fechaHora:").append(fechaHora);
        sb.append(", cliente:").append(cliente.toString());
        sb.append(", empleado:").append(empleado.toString());
        sb.append(", estatus:").append(estatus);
        sb.append(", listDV:").append(listDV);
        sb.append('}');
        return sb.toString();
    }
    
    
}
