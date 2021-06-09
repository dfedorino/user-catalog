package com.dfedorino.user_catalog.repository;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String login;
    private String password;
    private String email;
}
