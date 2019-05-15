package ua.tihonchik.dmitriy.entities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class EventImpl implements Event {

    private int id;
    private int userId;
    private int description;
    private LocalDateTime eventDate;
    private boolean activeEvent;
    private String reminderExpression;

    public EventImpl() {
    }

    public EventImpl(int userId, int description, LocalDateTime eventDate, boolean activeEvent, String reminderExpression) {
        this.userId = userId;
        this.description = description;
        this.eventDate = eventDate;
        this.activeEvent = activeEvent;
        this.reminderExpression = reminderExpression;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public boolean isActiveEvent() {
        return activeEvent;
    }

    public void setActiveEvent(boolean activeEvent) {
        this.activeEvent = activeEvent;
    }

    public String getReminderExpression() {
        return reminderExpression;
    }

    public void setReminderExpression(String reminderExpression) {
        this.reminderExpression = reminderExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventImpl event = (EventImpl) o;
        return id == event.id &&
                userId == event.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
