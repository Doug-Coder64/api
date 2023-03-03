package com.social.api.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.api.domain.Post;
import com.social.api.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {
   
    Set<Post> findAllByUserIn(Set<User> users);

    Set<Post> findByDescriptionIgnoreCase(String description);
} 
