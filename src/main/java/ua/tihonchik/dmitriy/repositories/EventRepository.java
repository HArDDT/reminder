package ua.tihonchik.dmitriy.repositories;

import ua.tihonchik.dmitriy.entities.Event;

import java.util.Collection;

public interface EventRepository {

    Object createEvent(Event event);

    Event getEvent(Object eventId);

    void updateEvent(Event event);

    void deleteEvent(Object eventId);

    Collection<Event> getEvents(Object userId);

}
