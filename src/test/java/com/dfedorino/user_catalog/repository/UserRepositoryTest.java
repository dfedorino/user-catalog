package com.dfedorino.user_catalog.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;

    @Test
    public void testCrudOperations() {
        User james = new User();
        james.setLogin("James");
        james.setPassword("pass1");
        james.setEmail("james@test.com");

        Contact jamesContact = new Contact();
        jamesContact.setZipCode("123456");
        jamesContact.setStreet("Street 1");
        jamesContact.setPhoneNumber("123-456-789");

        james.setContact(jamesContact);

        User josh = new User();
        josh.setLogin("Josh");
        josh.setPassword("pass2");
        josh.setEmail("josh@test.com");

        Contact joshContact = new Contact();
        jamesContact.setZipCode("654321");
        jamesContact.setStreet("Street 2");
        jamesContact.setPhoneNumber("987-654-321");

        josh.setContact(joshContact);

        // create
        entityManager.persist(james);
        entityManager.persist(josh);
        assertThat(repository.findByLogin("James")).isEqualTo(james);
        assertThat(repository.findByLogin("Josh")).isEqualTo(josh);

        // update
        User updatedJames = repository.findByLogin("James");
        updatedJames.setPassword("newPass");
        repository.save(updatedJames);
        updatedJames = repository.findByLogin("James");
        assertThat(updatedJames.getPassword()).isEqualTo("newPass");
        assertThat(repository.findByLogin("Josh")).isEqualTo(josh);

        // delete
        repository.deleteByLogin("James");
        User deletedJames = repository.findByLogin("James");
        assertThat(deletedJames).isNull();
        assertThat(repository.findByLogin("Josh")).isEqualTo(josh);
    }
}