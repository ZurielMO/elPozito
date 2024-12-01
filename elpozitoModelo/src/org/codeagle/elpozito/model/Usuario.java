package org.codeagle.elpozito.model;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasenia;
    private String rol;
    private String token;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String contrasenia, String rol, String token) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.token = token;
    }

    public Usuario(int idUsuario, String nombreUsuario, String contrasenia, String rol, String token) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.token = token;
    }

   

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    

    public void encode(){
        this.nombreUsuario=DigestUtils.md5Hex(this.nombreUsuario);
        this.contrasenia=DigestUtils.md5Hex(this.contrasenia);
    }
    
    public void colocarToken(){
        String p1=this.nombreUsuario;
        String p2="codeagle";
        Date hora= new Date();
        String p3=hora.toString();
        String token=p1+"-"+p2+"-"+p3;
        this.token=DigestUtils.sha512_256Hex(token);
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario{");
        sb.append("idUsuario:").append(idUsuario);
        sb.append(", nombreUsuario:").append(nombreUsuario);
        sb.append(", contrasenia:").append(contrasenia);
        sb.append(", rol:").append(rol);
        sb.append('}');
        return sb.toString();
    }
}