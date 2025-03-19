package com.poly.sms.service;

import java.util.List;
import com.poly.sms.entity.Email;

public interface EmailService {
    long getUnreadEmailCount();

    Email saveEmail(Email email);

    Email getEmailById(Integer id);

    void deleteEmail(Integer id);

    void sendEmail(Email email);

    List<Email> findAll();

    void sendPasswordWithCredentials(String to, String password);
}
