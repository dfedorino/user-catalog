package com.dfedorino.user_catalog.application;

import com.dfedorino.user_catalog.presentation.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;

    @Test
    public void testCrudOperations() {
        User james = new User("James", "Gosling", LocalDate.of(1970, Month.JANUARY, 1), "Address", "Number");
        User josh = new User("Josh", "Bloch", LocalDate.of(1970, Month.JANUARY, 2), "Other Address", "Other Number");

        // create
        entityManager.persist(james);
        entityManager.persist(josh);
        assertThat(repository.findByFamilyName("Gosling")).isEqualTo(james);
        assertThat(repository.findByFamilyName("Bloch")).isEqualTo(josh);

        // update
        User updatedJames = repository.findByFamilyName("Gosling");
        updatedJames.setAddress("New Address");
        repository.save(updatedJames);
        updatedJames = repository.findByFamilyName("Gosling");
        assertThat(updatedJames.getAddress()).isEqualTo("New Address");
        assertThat(repository.findByFamilyName("Bloch")).isEqualTo(josh);

        // delete
        repository.deleteByFamilyName("Gosling");
        User deletedJames = repository.findByFamilyName("Gosling");
        assertThat(deletedJames).isNull();
        assertThat(repository.findByFamilyName("Bloch")).isEqualTo(josh);
    }
}