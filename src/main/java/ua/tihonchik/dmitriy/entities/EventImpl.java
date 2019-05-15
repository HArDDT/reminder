package ua.tihonchik.dmitriy.entities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class EventImpl implements Event {

    private int id;
    private int userId;
    private String description;
    private LocalDateTime eventDate;
    private boolean activeEvent;
    private String reminderExpression;

    public EventImpl() {
    }

    public EventImpl(int userId, String description, String eventDate, boolean activeEvent, String reminderExpression) {
        this.userId = userId;
        this.description = description;
        this.eventDate = ZonedDateTime.parse(eventDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime();
        this.activeEvent = activeEvent;
        this.reminderExpression = reminderExpression;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    @Override
    public void setEventDate(String eventDate) {
        this.eventDate = ZonedDateTime.parse(eventDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime();
    }

    @Override
    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean isActiveEvent() {
        return activeEvent;
    }

    @Override
    public void setActiveEvent(boolean activeEvent) {
        this.activeEvent = activeEvent;
    }

    @Override
    public String getReminderExpression() {
        return reminderExpression;
    }

    @Override
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
