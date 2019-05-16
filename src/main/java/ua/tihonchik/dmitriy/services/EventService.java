package ua.tihonchik.dmitriy.services;

import ua.tihonchik.dmitriy.entities.Event;

import java.util.Collection;

public interface EventService {

    int createEvent(Event event);

    Event getEvent(int eventId, int userId);

    void updateEvent(Event event);

    void deleteEvent(int eventId, int userId);

    Collection<Event> getEvents(int userId);

}
