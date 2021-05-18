package com.dfedorino.user_catalog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity                 // JPA annotation to make this object ready for storage in a JPA-based data store
@Getter
@EqualsAndHashCode
// Choose your inheritance strategy:
//@Inheritance(strategy=InheritanceType.JOINED)
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {
    @Id                 // JPA annotation to make this field a primary key
    @GeneratedValue     // JPA annotation to make this field automatically populated with the JPA provider
    private Long id;
}

