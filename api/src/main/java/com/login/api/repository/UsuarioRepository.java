package com.login.api.repository;

import com.login.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByuserName(String userName);
    Usuario findByEmail(String email);
}
