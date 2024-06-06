package com.yabeto.marvel.marvel_api.entities;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<GrantedPermission> permissions;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public RoleEnum getName() {
        return name;
    }


    public void setName(RoleEnum name) {
        this.name = name;
    }


    public static enum RoleEnum{
        CUSTOMER, AUDITOR
    }


    @Override
    public String getAuthority() {
        if(name == null) return null;

        return "ROLE_" + name.name();
    }


    public List<GrantedPermission> getPermissions() {
        return permissions;
    }


    public void setPermissions(List<GrantedPermission> permissions) {
        this.permissions = permissions;
    }
}
