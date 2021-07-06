package com.dfedorino.user_catalog.repository;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "salt")
    private String salt;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( // a foreign key column
            name = "contact_id", // name of the column
            referencedColumnName = "contact_id" // name of the primary attribute
    )
    private Contact contact;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id", referencedColumnName = "authority_id")
    private Authority authority;
}
