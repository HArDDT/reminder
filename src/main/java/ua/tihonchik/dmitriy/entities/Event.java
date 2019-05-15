package ua.tihonchik.dmitriy.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

 @JsonDeserialize(as=EventImpl.class)
 public interface Event {

     int getId();

     void setId(int id);

     int getUserId();

     void setUserId(int userId);

     String getDescription();

     void setDescription(String description);

     LocalDateTime getEventDate();

     void setEventDate(LocalDateTime eventDate);

     void setEventDate(String eventDate);

     boolean isActiveEvent();

     void setActiveEvent(boolean activeEvent);

     String getReminderExpression();

     void setReminderExpression(String reminderExpression);
    
}
