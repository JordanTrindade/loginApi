package com.login.api.dto;

import com.login.api.entity.Usuario;
import com.login.api.enums.Roles;

import javax.management.relation.Role;

public record UsuarioDto (String userName, String email, String senha, Roles userRoles){
    public Usuario toUser() {
        return new Usuario(userName, email, senha, userRoles );
    }
}
