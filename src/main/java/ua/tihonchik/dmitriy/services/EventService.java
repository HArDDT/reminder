package ua.tihonchik.dmitriy.services;

import ua.tihonchik.dmitriy.entities.Event;

import java.util.Collection;

public interface EventService {

    void createEvent(Event event);
    void updateEvent(int id, Event event);
    void deleteEvent(int id);
    Collection<Event> getEvents();

}
