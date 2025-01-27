package com.login.api.controller;

import com.login.api.dto.AuthRequestDTO;
import com.login.api.dto.RecoveryJwtTokenDto;
import com.login.api.dto.UsuarioDto;
import com.login.api.entity.Usuario;
import com.login.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String auth(){
        return "auntenticacao liberado";
    }

    @PostMapping(value = "login")
    public ResponseEntity<RecoveryJwtTokenDto> Login(@RequestBody AuthRequestDTO request ){
        RecoveryJwtTokenDto token =  usuarioService.recoverTokenLogin(request);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Usuario> registerUser(@RequestBody UsuarioDto user){

        Usuario userSalvo = usuarioService.addUser(user);
        URI uri = ServletUriComponentsBuilder
                . fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(userSalvo.getId())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
    }
}
