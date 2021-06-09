package com.dfedorino.user_catalog.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
    User findByEmail(String email);
    void deleteByLogin(String login);
}
