package com.social.api.domain.common;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Column(name = "date_created", nullable=false)
    private Timestamp dateCreated;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    public void onInsert() {
        dateCreated = Timestamp.from(Instant.now());
        updatedAt = dateCreated;
      }
}