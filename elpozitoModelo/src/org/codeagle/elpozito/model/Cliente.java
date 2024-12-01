package org.codeagle.elpozito.model;

public class Cliente {
    private int idCliente;
    private String fechaRegistro;
    private int estatus;
    private String email;
    private Persona persona;
 
    public Cliente() {
    }
 
    public Cliente(String fechaRegistro, int estatus, String email, Persona persona) {
        this.fechaRegistro = fechaRegistro;
        this.estatus = estatus;
        this.email = email;
        this.persona = persona;
    }
 
    public Cliente(int idCliente, String fechaRegistro, int estatus, String email, Persona persona) {
        this.idCliente = idCliente;
        this.fechaRegistro = fechaRegistro;
        this.estatus = estatus;
        this.email = email;
        this.persona = persona;
    }
 
    public int getIdCliente() {
        return idCliente;
    }
 
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
 
    public String getFechaRegistro() {
        return fechaRegistro;
    }
 
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
 
    public int getEstatus() {
        return estatus;
    }
 
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Persona getPersona() {
        return persona;
    }
 
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("idCliente=").append(idCliente);
        sb.append(", fechaRegistro=").append(fechaRegistro);
        sb.append(", estatus=").append(estatus);
        sb.append(", email=").append(email);
        sb.append(", persona=").append(persona);
        sb.append('}');
        return sb.toString();
    }

}
