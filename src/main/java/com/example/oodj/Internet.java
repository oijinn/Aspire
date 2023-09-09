package com.example.oodj;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.Random;

public class Internet {

    private static final String aspire_email = "aspire.com.my@gmail.com";
    private static final String aspire_password = "Aspire123";
    private static String code = "";

    public static boolean check_internet_connection() {
        try {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static String generate_code(){

        String candidates =  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int length = 7;

        StringBuilder random_code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            random_code.append(candidates.charAt(random.nextInt(candidates.length())));
        }

        return random_code.toString();

    }

    public static void send_code(String recipient, String code) throws Exception {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(aspire_email, aspire_password);
            }
        });

        Message message = new MimeMessage(session);
        message.setSubject("Account Recovery");
        message.setContent("<h1 style=\"color:#FF6D1B; font-size: 4em;\" >" + code + "</h1> <br/> " +
                "<p> This is the code for account recovery purpose, please don't show it to anyone. </p> <br/> " +
                "<p> Cheers, </p> <p> The Aspire Team </p> ", "text/html");

        Address addressTo = new InternetAddress(recipient);
        message.setRecipient(Message.RecipientType.TO, addressTo);

        Transport.send(message);

    }

    public static void send_receipt(String recipient, String [] appointment_details, String [] feedback_details, String [] payment_details, String selected_photo) throws Exception {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(aspire_email, aspire_password);
            }
        });

        Message message = new MimeMessage(session);
        message.setSubject("Receipt for " + appointment_details[0]);

        Address addressTo = new InternetAddress(recipient);
        message.setRecipient(Message.RecipientType.TO, addressTo);

        MimeMultipart multipart = new MimeMultipart();

        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(new File(selected_photo));

        MimeBodyPart message_body_part = new MimeBodyPart();
        message_body_part.setContent(
                "<h1 style=\" font-size: 2em;\" >" + appointment_details[0] + "</h1> <br/> " +
                        "<p style=\" color:#48C55C;\" > Status: " + appointment_details[1] + "</p>" +
                        "<p> Title: " + appointment_details[2] + "</p>" +
                        "<p> Date: " + appointment_details[3] + "</p>" +
                        "<p> Time: " + appointment_details[4] + "</p>" +
                        "<p> Appliance: " + appointment_details[5] + "</p>" +
                        "<p> Description: " + appointment_details[6] + "</p> <br/>" +

                        "<p> Completed by: " + feedback_details[0] + "</p>" +
                        "<p> Feedback: " + feedback_details[1] + "</p> <br/>" +

                        "<p> Price: " + payment_details[0] + "</p>" +
                        "<p> Service tax: " + payment_details[1] + "</p>" +
                        "<p> Total: " + payment_details[2] + "</p>" +
                        "<p> Received: " + payment_details[3] + "</p>" +
                        "<p> Change: " + payment_details[4] + "</p> <br/>" +

                        "<p> Hope you enjoy our service, come again! </p> <br/> " +
                        "<p> Cheers, </p> <p> The Aspire Team </p> ", "text/html");

        multipart.addBodyPart(message_body_part);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);

        Transport.send(message);

    }

    public static void set_code(String input_code){
        code = input_code;
    }

    public static String get_code(){return code;}

}