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
        User james = new UserBuilder().login("James").password("pass1").email("james@test.com").build();
        User josh = new UserBuilder().login("Josh").password("pass2").email("josh@test.com").build();

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