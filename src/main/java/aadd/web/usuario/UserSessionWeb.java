package aadd.web.usuario;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.TipoUsuario;

@Named
@SessionScoped
public class UserSessionWeb implements Serializable {
    protected UsuarioDTO usuario;
         
    public boolean isLogin() {
        return usuario != null;
    }
    public boolean isAdmin() {
        return usuario.getTipo().equals(TipoUsuario.ADMIN);
    }
    public boolean isRestaurante() {
        return usuario.getTipo().equals(TipoUsuario.RESTAURANTE);
    }
    public boolean isRider() {
        return usuario.getTipo().equals(TipoUsuario.RIDER);
    }
   
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}