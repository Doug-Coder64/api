package com.social.api.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
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

import com.social.api.domain.User;
import com.social.api.repository.UserRepository;
import com.social.api.domain.util.ErrorHandler;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<Set<User>> getAllUsers(@RequestParam(required = false) String userName, @RequestParam(required = false) String email){
        try {

            //Hashset so that if username and email are provided as parameters user profile only added once
            Set<User> users = new HashSet<User>();

            if (userName == null && email == null) 
                userRepository.findAll().forEach(users::add);

            if(userName != null)
                userRepository.findByUserName(userName).forEach(users::add);

            if(email != null)
                userRepository.findByEmail(email).forEach(users::add);
            
            if (users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(users, HttpStatus.OK);
        
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createTutorial(@RequestBody User user) {
		try {

            if(!userRepository.existsByEmail(user.getEmail())){
			User _user = userRepository
					.save(new User(user.getUserName(), user.getEmail(), user.getFirstName(), user.getLastName(),user.isActiveStatus(), user.getPostList()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Object>("Email already exists", HttpStatus.BAD_REQUEST);
            }
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ErrorHandler(e).getError(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent() && !userRepository.existsByEmail(user.getEmail())) {
			User _user = userData.get();
			_user.setUserName(user.getUserName());
            _user.setEmail(user.getEmail());
            _user.setFirstName(user.getFirstName());
            _user.setLastName(user.getLastName());
            _user.setActiveStatus(user.isActiveStatus());
			return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @GetMapping("/users/Active")
	public ResponseEntity<Set<User>> findByActiveStatus() {
		try {
			Set<User> users = userRepository.findByActiveStatus(true);

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
            return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
            String errorMessage = e.toString();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
