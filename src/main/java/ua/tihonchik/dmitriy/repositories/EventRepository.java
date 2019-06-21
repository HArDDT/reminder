package ua.tihonchik.dmitriy.repositories;

import ua.tihonchik.dmitriy.entities.Event;

import java.util.Collection;

public interface EventRepository {

    int createEvent(Event event);

    Event getEvent(int eventId);

    void updateEvent(Event event);

    void deleteEvent(int eventId);

    Collection<Event> getEvents(int userId);

}
