package com.social.api.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.api.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
   
    Set<Post> findByUserId(long userId);

    Set<Post> findByUserIds(Set<Long> userIds);

    Set<Post> findByEmail(String email);

    Set<Post> findByDescriptionIgnoreCase(String description);
} 
