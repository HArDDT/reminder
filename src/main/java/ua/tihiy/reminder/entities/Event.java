package ua.tihiy.reminder.entities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class Event {

    private int id;
    private int userId;
    private String description;
    private LocalDateTime eventDate;
    private boolean activeEvent;
    private String reminderExpression;

    public Event() {
    }

    public Event(int userId, String description, String eventDate, boolean activeEvent, String reminderExpression) {
        this.userId = userId;
        this.description = description;
        this.eventDate = ZonedDateTime.parse(eventDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime();
        this.activeEvent = activeEvent;
        this.reminderExpression = reminderExpression;
    }

    public Event(int id, int userId, String description, String eventDate, boolean activeEvent, String reminderExpression) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.eventDate = ZonedDateTime.parse(eventDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime();
        this.activeEvent = activeEvent;
        this.reminderExpression = reminderExpression;
    }

    public Event(int id, int userId, String description, LocalDateTime eventDate, boolean activeEvent, String reminderExpression) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                userId == event.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
