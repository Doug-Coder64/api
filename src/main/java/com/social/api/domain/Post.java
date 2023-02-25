package com.social.api.domain;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

import com.social.api.domain.common.BaseEntity;

@Entity
@Table(name = "POSTS")
public class Post extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post parentPost;

    @OneToMany(mappedBy = "parentPost")
    private Set<Post> subPosts = new HashSet<>();

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }


    public Post() {}

    public Post(String description){
        this.description = description;
    }

    public String toString(){
        return "Post{" + "description=" + description+"}";
    }

}
