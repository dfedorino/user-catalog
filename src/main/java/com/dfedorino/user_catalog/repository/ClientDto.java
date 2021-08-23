package com.dfedorino.user_catalog.repository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ClientDtoImpl.class)
public interface ClientDto {
    String getLogin();
    String getEmail();
    String getPhoneNumber();
    String getZipCode();
    String getStreet();
}
