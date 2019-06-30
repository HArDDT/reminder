package ua.tihiy.reminder.events.repeatable;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RepeatableEventServiceImpl implements RepeatableEventService {

    private RepeatableEventRepository repository;

    public RepeatableEventServiceImpl(RepeatableEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public int createEvent(RepeatableEvent repeatableEvent) {
        return repository.createEvent(repeatableEvent);
    }

    @Override
    public RepeatableEvent getEvent(int eventId) {
        return repository.getEvent(eventId);
    }

    @Override
    public void updateEvent(RepeatableEvent repeatableEvent) {
        repository.updateEvent(repeatableEvent);
    }

    @Override
    public void deleteEvent(int eventId) {
        repository.deleteEvent(eventId);
    }

    @Override
    public Collection<RepeatableEvent> getEvents(int userId) {
        return repository.getEvents(userId);
    }

}
