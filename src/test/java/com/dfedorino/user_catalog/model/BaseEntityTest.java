package com.dfedorino.user_catalog.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest {

    @Test
    public void testGetId() {
        BaseEntity userEntity = new User();
        assertThat(userEntity.getId()).isNull();
    }

    @Test
    public void testEquals_IdIsNull_AreEqual() {
        BaseEntity firstUserEntity = new User();
        BaseEntity secondUserEntity = new User();
        assertThat(firstUserEntity).isEqualTo(secondUserEntity);
        assertThat(firstUserEntity.hashCode()).isEqualTo(secondUserEntity.hashCode());
    }
}