package ru.hogwarts.school2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {

    Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private Integer port;

    @Override
    public String getPort() {
        logger.info("Was invoked method for getting system port number");
        String gottenPortPhrase = "System port number is " + port.toString();
        return gottenPortPhrase;
    }
}
