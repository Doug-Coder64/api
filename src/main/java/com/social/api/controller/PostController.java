package com.social.api.controller;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.api.domain.Post;
import com.social.api.domain.User;
import com.social.api.repository.*;
import com.social.api.domain.util.ErrorHandler;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PostController {
    
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/posts")
    public ResponseEntity<Set<Post>> getAllPosts(@RequestParam(required = false) String userName, @RequestParam(required = false) String email, @RequestParam(required = false) Long userId){
        try {

            //Hashset so that if username and email are provided as parameters user profile only added once
            Set<Post> posts = new HashSet<Post>();
            Set<User> users = new HashSet<User>();
            Set<Long> userIds = new HashSet<Long>();
            

            if (userName == null && email == null && userId == null) 
                postRepository.findAll().forEach(posts::add);

            if(userName != null){
                userRepository.findByUserName(userName).forEach(users::add);
                //ADD User ID logic
                postRepository.findByUserIds(userIds).forEach(posts::add);
            }

            if(email != null){
                postRepository.findByEmail(email).forEach(posts::add);
            }
            if(userId != null)
                postRepository.findByUserId(userId).forEach(posts::add);

            if (posts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(posts, HttpStatus.OK);
        
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }}
}
