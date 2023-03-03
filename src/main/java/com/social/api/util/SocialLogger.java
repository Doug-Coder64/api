package com.social.api.util;

import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;

import com.social.api.domain.util.LogItem;
import com.social.api.repository.LogRepository;

@Service
public class SocialLogger  {

    private static final Logger LOGGER = LogManager.getLogger(SocialLogger.class);

    public void info (String message, LogRepository logRepository) {
        String date = new Date().toString();
        logRepository.insert(new LogItem("info", message, date));
        LOGGER.info(message);
    }

    public void warn(String message, LogRepository logRepository) {
        String date = new Date().toString();
        logRepository.insert(new LogItem("warn", message, date));
        LOGGER.warn(message);
    }

    public void error(String message, LogRepository logRepository) {
        String date = new Date().toString();
        logRepository.insert(new LogItem("error", message, date));
        LOGGER.error(message);
    }
    
}
