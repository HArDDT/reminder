package ua.tihonchik.dmitriy.services;

import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.SimplifiedUser;

import java.util.List;
import java.util.Map;

public interface SchedulingService {

    Map<SimplifiedUser, List<Event>> getData();

    Map<String, String> emailsWithMessage();

    void sendEmails();
}
