package com.google.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationServiceNoOpImpl implements NotificationService {
    private static Logger logger = LoggerFactory.getLogger(NotificationServiceNoOpImpl.class);

    @Override
    public void sendNotification(String notificationSubject, String notificationBody) {
        logger.info("================ SENDING NOTIFICATION ================");
        logger.info("Notification Subject: " + notificationSubject);
        logger.info("Notification Body: " + notificationBody);
        logger.info("================ NOTIFICATION SENT ================");
    }

}
