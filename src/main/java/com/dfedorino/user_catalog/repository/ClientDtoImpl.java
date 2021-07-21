package com.dfedorino.user_catalog.repository;

import lombok.Value;

@Value
public class ClientDtoImpl implements ClientDto {
    private User user;

    @Override
    public String getLogin() {
        return user.getLogin();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getPhoneNumber() {
        return user.getContact() == null ? null : user.getContact().getPhoneNumber();
    }

    @Override
    public String getZipCode() {
        return user.getContact() == null ? null : String.valueOf(user.getContact().getZipCode());
    }

    @Override
    public String getStreet() {
        return user.getContact() == null ? null : user.getContact().getStreet();
    }
}
