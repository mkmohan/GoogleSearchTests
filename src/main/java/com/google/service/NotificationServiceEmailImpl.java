package com.google.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//NOTE: Example implementation. This is provided only for demo. Not tested!
public class NotificationServiceEmailImpl implements NotificationService {

    private static Logger logger = LoggerFactory.getLogger(NotificationServiceEmailImpl.class);

    @Override
    public void sendNotification(String notificationSubject, String notificationBody) {
        logger.info("================ SENDING EMAIL NOTIFICATION ================");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        String fromEmail = System.getProperty("fromEmail");
        String toEmail = System.getProperty("toEmail");
        String password = System.getProperty("password");
        send(properties, fromEmail, password, toEmail, notificationSubject, notificationBody);

        logger.info("================ EMAIL NOTIFICATION SENT ================");
    }

    public void send(Properties properties, String fromEmail, String password, String toEmail, String emailSubject, String emailBody) {
        //get Session
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(emailSubject);
            message.setText(emailBody);
            //send message
            Transport.send(message);
            logger.debug("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
