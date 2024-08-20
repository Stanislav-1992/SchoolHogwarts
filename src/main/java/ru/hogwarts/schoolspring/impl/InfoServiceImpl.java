package ru.hogwarts.schoolspring.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.service.InfoService;

@Service
@Profile("test")
public class InfoServiceImpl implements InfoService {
        @Value("${server.port}")
        private String currentPort;

        private final Logger log = LoggerFactory.getLogger (InfoService.class);

        public String getCurrentPort () {
            log.info("current port: {}", currentPort);
            return currentPort;
        }
}
