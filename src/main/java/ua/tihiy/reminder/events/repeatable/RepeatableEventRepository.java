package ua.tihiy.reminder.events.repeatable;

import java.util.Collection;

public interface RepeatableEventRepository {

    int createEvent(RepeatableEvent repeatableEvent);

    RepeatableEvent getEvent(int eventId);

    void updateEvent(RepeatableEvent repeatableEvent);

    void deleteEvent(int eventId);

    Collection<RepeatableEvent> getEvents(int userId);

}
