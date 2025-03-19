
package com.poly.sms.service.impl;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.poly.sms.entity.Email;
import com.poly.sms.repository.EmailRepository;
import com.poly.sms.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailRepository emailRepository;

    @Override
    public long getUnreadEmailCount() {
        return emailRepository.countByIsReadFalse();
    }

    @Override
    public Email saveEmail(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public Email getEmailById(Integer id) {
        return emailRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteEmail(Integer id) {
        emailRepository.deleteById(id);
    }

    @Override
    public void sendEmail(Email email) {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.debug", "true");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("hoangthanhtuyen.ksmy@gmail.com");
        message.setSubject("New message from " + email.getSender());
        message.setText(email.getContent() + "\n\nFrom: " + email.getSubject());

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }

    }

    @Override
    public void sendPasswordWithCredentials(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Mật khẩu của bạn");
        message.setText("Mật khẩu của bạn là: " + password);
        mailSender.send(message);
    }
 
    @Override
    public List<Email> findAll() {
        return emailRepository.findAll();
    }
}
