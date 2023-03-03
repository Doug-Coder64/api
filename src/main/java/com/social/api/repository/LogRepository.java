package com.social.api.repository;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.Repository;

import com.social.api.domain.util.LogItem;

public interface LogRepository extends MongoRepository<LogItem, Long>{
    public long count();
}
