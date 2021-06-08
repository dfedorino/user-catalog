package com.dfedorino.user_catalog.repository;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Data
@Entity(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class User extends BaseEntity {
    private String login;
    private String password;
    private String email;
}
