package com.dfedorino.user_catalog.repository;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Data
@Entity(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_id")
    private Long id;
    @Column(name = "zip")
    @Pattern(regexp="(^$|[0-9]{6})")
    private String zipCode;
    @Column(name = "street")
    private String street;
    @Column(name = "phone")
    @Pattern(regexp="(^$|[0-9]{11})")
    private String phoneNumber;
}
