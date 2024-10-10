/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/8/24
 * Time: 12:57 AM
 */

package com.smatech.smatech_shop_app.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService{

    private final JavaMailSender mailSender;

    private final Environment environment;


    @Override
    public void sendEmail(String to, String subject, String body) {
        mailSender(to, subject, body);
    }

    private void mailSender(String to, String subject, String body) {
        try{
            log.info("Composing message");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String fromEmailAddress = environment.getProperty("spring.mail.username");
            helper.setSubject(subject);
            assert fromEmailAddress != null;
            helper.setFrom(fromEmailAddress, "Smatech Shop Application");
            helper.setTo(to);
            helper.setReplyTo(fromEmailAddress);
            helper.setText(body);

            log.info("message composed");
            mailSender.send(message);
            log.info("Email sent");
        }catch (Exception e){
            log.info("Error sending email:::: {}", e.getMessage());
            log.error("Stack Trace {}", e.getStackTrace());
        }
    }
}
