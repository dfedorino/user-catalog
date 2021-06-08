package com.dfedorino.user_catalog.repository;

import com.dfedorino.user_catalog.repository.BaseEntity;
import com.dfedorino.user_catalog.repository.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest {

    @Test
    public void testGetId() {
        BaseEntity userEntity = new UserBuilder().build();
        assertThat(userEntity.getId()).isNull();
    }

    @Test
    public void testEquals_IdIsNull_AreEqual() {
        BaseEntity firstUserEntity = new UserBuilder().build();
        BaseEntity secondUserEntity = new UserBuilder().build();
        assertThat(firstUserEntity).isEqualTo(secondUserEntity);
        assertThat(firstUserEntity.hashCode()).isEqualTo(secondUserEntity.hashCode());
    }
}