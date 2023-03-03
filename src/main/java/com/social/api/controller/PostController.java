package com.social.api.controller;

import java.util.List;
import java.util.Set;

import java.util.HashSet;

import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.api.domain.Post;
import com.social.api.domain.User;
import com.social.api.domain.util.ErrorHandler;
import com.social.api.repository.*;
import com.social.api.util.SocialLogger;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LogRepository logRepository;

    private SocialLogger logger = new SocialLogger();

    @GetMapping("/posts/{userId}")
    public ResponseEntity<Object> getPostsByUserId(@PathVariable(value = "userId") Long userId) {
        try {
            logger.info("Finding posts for userId: " + userId, logRepository);
            // Hashset so that if username and email are provided as parameters user profile
            // only added once
            Set<Post> posts = new HashSet<Post>();
            Set<Long> userIds = new HashSet<Long>();

            if (userId != null)
                userIds.add(userId);

            if (userIds != null)
                postRepository.findAllByUserIn(userRepository.findAllByIdIn(userIds)).forEach(posts::add);

            if (posts.isEmpty()) {
                logger.warn("No posts for userId(s): " + userIds.toString(), logRepository);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            logger.info(posts.toString(), logRepository);
            return new ResponseEntity<>(posts, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e.toString(), logRepository);
            return new ResponseEntity<Object>(new ErrorHandler(e).getError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/all")
    public ResponseEntity<Object> getAllPosts() {
        try {

            List<Post> posts;
            posts = postRepository.findAll();

            if (posts.isEmpty()) {
                logger.warn("No posts found", logRepository);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(posts.toString(), logRepository);
            return new ResponseEntity<>(posts, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e.toString(), logRepository);
            return new ResponseEntity<Object>(new ErrorHandler(e).getError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/post/{userId}")
    public ResponseEntity<Object> createPost(@PathVariable(value = "userId") Long userId,
            @RequestBody(required = true) Post post) {
        try {
            if (post.getParentPost() != null) {

                Post _post = postRepository.save(new Post(post.getDescription(), userRepository.findById(userId).get(),
                        post.getIsQuoted(), post.getParentPost()));
                logger.info(_post.toString(), logRepository);
                return new ResponseEntity<>(_post, HttpStatus.CREATED);
            }

            Post _post = postRepository
                    .save(new Post(post.getDescription(), userRepository.findById(userId).get()));
            logger.info(_post.toString(), logRepository);
            return new ResponseEntity<>(_post, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error(e.toString(), logRepository);
            return new ResponseEntity<Object>(new ErrorHandler(e).getError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping("/post/comment")
    // public ResponseEntity<Object> commentPost(@RequestBody(required = true)Post
    // post){
    // try {

    // }
    // }
}
