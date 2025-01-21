package com.login.api.service;

import com.login.api.entity.Usuario;
import com.login.api.repository.UsuarioRepository;
import com.login.api.security.userDetails.UsarioDetailsImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByuserName(username);
        return new UsarioDetailsImplements(user);
    }
}
