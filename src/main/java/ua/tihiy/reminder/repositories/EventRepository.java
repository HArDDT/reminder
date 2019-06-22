package ua.tihiy.reminder.repositories;

import ua.tihiy.reminder.entities.Event;

import java.util.Collection;

public interface EventRepository {

    int createEvent(Event event);

    Event getEvent(int eventId);

    void updateEvent(Event event);

    void deleteEvent(int eventId);

    Collection<Event> getEvents(int userId);

}
