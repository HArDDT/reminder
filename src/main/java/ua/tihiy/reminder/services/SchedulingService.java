package ua.tihiy.reminder.services;

import ua.tihiy.reminder.entities.Event;
import ua.tihiy.reminder.entities.SimplifiedUser;

import java.util.List;
import java.util.Map;

public interface SchedulingService {

    Map<SimplifiedUser, List<Event>> getData();

    Map<String, String> emailsWithMessage();

    void sendEmails();
}
