package com.dfedorino.user_catalog.application;

import com.dfedorino.user_catalog.presentation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
