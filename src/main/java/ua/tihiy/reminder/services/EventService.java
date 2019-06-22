package ua.tihiy.reminder.services;

import ua.tihiy.reminder.entities.Event;

import java.util.Collection;

public interface EventService {

    int createEvent(Event event);

    Event getEvent(int eventId);

    void updateEvent(Event event);

    void deleteEvent(int eventId);

    Collection<Event> getEvents(int userId);

}
