package com.login.api.entity;

import com.login.api.enums.Roles;
import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    @Column(unique = true)
    private String email;

    private String senha;

    private Roles UserRoles;

    public Usuario(){}

    public Usuario(String userName, String email, String senha ){
        this.userName = userName;
        this.email = email;
        this.senha = senha;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Roles getUserRoles() {
        return UserRoles;
    }

    public void setUserRoles(Roles userRoles) {
        UserRoles = userRoles;
    }

    public Long getId() {
        return id;
    }
}
