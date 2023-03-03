package com.social.api.domain;

import java.util.Set;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.social.api.domain.common.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_name", nullable = false)
    private String userName;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "active_status", nullable = false)
    private boolean activeStatus;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private Set<Post> postList;

    public long getId() {
        return id;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Boolean isActiveStatus() {
        return activeStatus;
    }

    public void setPostList(Set<Post> postList) {
        this.postList = postList;
    }
    public Set<Post> getPostList() {
        return postList;
    }

    public User() {
    }

    public User(String userName, String email, String firstName, 
                String lastName, boolean activeStatus, Set<Post> postList) {
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activeStatus = activeStatus;
        this.postList = postList;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", email=" + email + ", firstName" 
        + firstName + ", lastName=" + lastName + ", isActive="+ activeStatus + "]";
    }
}