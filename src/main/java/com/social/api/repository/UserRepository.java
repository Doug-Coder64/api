package com.social.api.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.api.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Set<User> findAllByIdIn(Set<Long> id);
    Set<User> findByEmail(String email);

    Set<User> findByUserName(String userName);

    Set<User> findByActiveStatus(Boolean isActive);

    public boolean existsByEmail(String email);
}
