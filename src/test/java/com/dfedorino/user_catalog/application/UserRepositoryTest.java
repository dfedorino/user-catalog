package com.dfedorino.user_catalog.application;

import com.dfedorino.user_catalog.presentation.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;

    @Test
    public void testCreateUser() {
        String firstName = "James";
        String familyName = "Gosling";
        LocalDate dateOfBirth = LocalDate.of(1970, Month.JANUARY, 1);
        String address = "Address";
        String phone_number = "Phone Number";
        User entity = new User(firstName, familyName, dateOfBirth, address, phone_number);
        entityManager.persist(entity);
        assertThat(repository.getOne(1L)).isEqualTo(entity);
    }
}