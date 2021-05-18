package com.dfedorino.user_catalog.presentation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class User extends BaseEntity {
    private String firstName;
    private String familyName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
}
