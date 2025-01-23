package com.login.api.controller;

import com.login.api.dto.UsuarioDto;
import com.login.api.entity.Usuario;
import com.login.api.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/me")
    public Usuario getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return usuarioService.getUsuarioByUsername(username);
    }
}
