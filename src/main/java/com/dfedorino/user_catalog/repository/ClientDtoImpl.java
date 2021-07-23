package com.dfedorino.user_catalog.repository;

import lombok.Data;

@Data
public class ClientDtoImpl implements ClientDto {
    private String login;
    private String email;
    private String phoneNumber;
    private String zipCode;
    private String street;

    public ClientDtoImpl() {}

    public ClientDtoImpl(User user) {
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.phoneNumber = user.getContact().getPhoneNumber();
        this.street = user.getContact().getStreet();
        this.zipCode = String.valueOf(user.getContact().getZipCode());
    }
}
