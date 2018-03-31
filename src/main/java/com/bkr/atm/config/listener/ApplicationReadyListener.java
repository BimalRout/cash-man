package com.bkr.atm.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bkr.atm.service.DefaultTestDataService;

@Component
public class ApplicationReadyListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationReadyListener.class);

    @Autowired
    private DefaultTestDataService defaultTestDataService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        defaultTestDataService.createDefaultTestDataIfNeeded();
    }

}
