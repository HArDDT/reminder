package ua.tihonchik.dmitriy.services;

public interface EmailService {

    void sendMessage(String to, String subject, String text);

}
