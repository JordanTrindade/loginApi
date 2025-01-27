package com.login.api.service;

import com.login.api.dto.AuthRequestDTO;
import com.login.api.dto.RecoveryJwtTokenDto;
import com.login.api.dto.UsuarioDto;
import com.login.api.entity.Usuario;
import com.login.api.repository.UsuarioRepository;
import com.login.api.security.userDetails.UserDetailsImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    @Lazy
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  UsuarioRepository usuarioRepository;
    @Autowired
    private  JWTTokenService  jwtTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByuserName(username);
        return new UserDetailsImplements(user);
    }

    public Usuario addUser(UsuarioDto usuarioDto){
        Usuario usuario = usuarioDto.toUser();
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByuserName(username);
    }

    public RecoveryJwtTokenDto recoverTokenLogin(AuthRequestDTO request) {

        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.userName(),request.senha());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImplements userDetailsImplements = (UserDetailsImplements) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.GerarToken(userDetailsImplements));
    }
}
