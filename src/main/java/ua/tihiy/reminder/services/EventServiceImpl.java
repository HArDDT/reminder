package ua.tihiy.reminder.services;

import org.springframework.stereotype.Service;
import ua.tihiy.reminder.entities.Event;
import ua.tihiy.reminder.repositories.EventRepository;

import java.util.Collection;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository repository;

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public int createEvent(Event event) {
        return repository.createEvent(event);
    }

    @Override
    public Event getEvent(int eventId) {
        return repository.getEvent(eventId);
    }

    @Override
    public void updateEvent(Event event) {
        repository.updateEvent(event);
    }

    @Override
    public void deleteEvent(int eventId) {
        repository.deleteEvent(eventId);
    }

    @Override
    public Collection<Event> getEvents(int userId) {
        return repository.getEvents(userId);
    }

}
