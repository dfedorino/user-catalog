package com.dfedorino.user_catalog.application;

import com.dfedorino.user_catalog.presentation.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void testCrudOperations_EmptyDb() {
        User james = new User("James", "Gosling", LocalDate.of(1970, Month.JANUARY, 1), "Address", "Number");
        User josh = new User("Josh", "Bloch", LocalDate.of(1970, Month.JANUARY, 2), "Other Address", "Other Number");

        // create
        repository.save(james);
        repository.save(josh);
        assertThat(repository.getOne(1L)).isEqualTo(james);
        assertThat(repository.getOne(2L)).isEqualTo(josh);

        // update
        User updatedJames = repository.getOne(1L);
        updatedJames.setAddress("New Address");
        repository.save(updatedJames);
        updatedJames = repository.getOne(1L);
        assertThat(updatedJames.getAddress()).isEqualTo("New Address");
        assertThat(repository.getOne(2L)).isEqualTo(josh);

        // delete
        repository.deleteById(1L);
        Optional<User> deletedJames = repository.findById(1L);
        assertThat(deletedJames).isEmpty();
        assertThat(repository.getOne(2L)).isEqualTo(josh);
    }
}