package com.dfedorino.user_catalog.application;

import com.dfedorino.user_catalog.presentation.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
    void deleteByLogin(String login);
}
