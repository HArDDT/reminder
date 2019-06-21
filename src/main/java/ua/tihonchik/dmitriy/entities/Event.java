package ua.tihonchik.dmitriy.entities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Component
public class Event {

    private Object id;
    private Object userId;
    private String description;
    private LocalDateTime eventDate;
    private boolean activeEvent;
    private String reminderExpression;

    public Event() {
    }

    public Event(Object userId, String description, String eventDate, boolean activeEvent, String reminderExpression) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.description = description;
        this.eventDate = ZonedDateTime.parse(eventDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime();
        this.activeEvent = activeEvent;
        this.reminderExpression = reminderExpression;
    }

    public Event(Object id, Object userId, String description, String eventDate, boolean activeEvent, String reminderExpression) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.eventDate = ZonedDateTime.parse(eventDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime();
        this.activeEvent = activeEvent;
        this.reminderExpression = reminderExpression;
    }

    public Event(Object id, Object userId, String description, LocalDateTime eventDate, boolean activeEvent, String reminderExpression) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.eventDate = eventDate;
        this.activeEvent = activeEvent;
        this.reminderExpression = reminderExpression;
    }

    
    public Object getId() {
        return id;
    }

    
    public void setId(Object id) {
        this.id = id;
    }

    
    public Object getUserId() {
        return userId;
    }

    
    public void setUserId(Object userId) {
        this.userId = userId;
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

    
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    
    public void setEventDate(String eventDate) {
        this.eventDate = ZonedDateTime.parse(eventDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime();
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

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(userId, event.userId);
    }

    
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
