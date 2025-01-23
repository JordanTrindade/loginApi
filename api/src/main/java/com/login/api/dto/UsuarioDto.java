package com.login.api.dto;

import com.login.api.entity.Usuario;

public record UsuarioDto (String userName, String email, String senha){
    public Usuario toUser() {
        return new Usuario(userName, email, senha );
    }
}
