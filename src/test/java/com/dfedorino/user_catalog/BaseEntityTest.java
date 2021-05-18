package com.dfedorino.user_catalog;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest {

    @Test
    public void testGetId() {
        BaseEntity userEntity = new User();
        assertThat(userEntity.getId()).isNull();
    }
}