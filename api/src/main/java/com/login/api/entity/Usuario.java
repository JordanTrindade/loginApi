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

    Usuario(){}

}
