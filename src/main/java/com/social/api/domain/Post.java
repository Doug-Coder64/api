package com.social.api.domain;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.*;

import com.social.api.domain.common.BaseEntity;

@Entity
@Table(name = "POSTS")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Description")
    private String description;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne()
    private Post parentPost;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPost")
    private Set<Post> subPosts = new HashSet<>();

    @Column(name = "child_post", nullable = false)
    private Boolean isChild;

    @Column(name = "quote_post")
    private Boolean isQuoted;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public User getUserId() {
        return user;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    public Post getParentPost() {
        return parentPost;
    }

    public void setIsQuoted(Boolean isQuoted) {
        this.isQuoted = isQuoted;
    }

    public Boolean getIsQuoted() {
        return isQuoted;
    }

    public Post() {
    }

    public Post(String description, User user) {
        this.isQuoted = false;
        this.description = description;
        this.isChild = false;
        this.user = user;
    }

    public Post(String description, User user, Boolean isQuoted, Post parentPost) {
        this.parentPost = parentPost;
        this.isQuoted = isQuoted;
        this.description = description;
        this.isChild = true;
        this.user = user;
    }

    public String toString() {
        return "Post{" + "description=" + description + " user=" + user + " parentPost=" + parentPost + " isQuoted="
                + isQuoted + "isChild=" + isChild + "}";
    }

}
