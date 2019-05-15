package ua.tihonchik.dmitriy.repositories;

import ua.tihonchik.dmitriy.entities.Event;

import java.util.Collection;

public interface EventRepository {

    int createEvent(Event event);

    Collection<Event> getEvents(int userId);

}
