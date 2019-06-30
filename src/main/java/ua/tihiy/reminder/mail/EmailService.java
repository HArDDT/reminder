package ua.tihiy.reminder.mail;

public interface EmailService {

    void sendMessage(String to, String subject, String text);

}
