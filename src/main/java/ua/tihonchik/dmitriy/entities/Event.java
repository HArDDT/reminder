package ua.tihonchik.dmitriy.entities;

import java.time.LocalDateTime;

 public interface Event {

     Object getId();

     void setId(Object id);

     Object getUserId();

     void setUserId(Object userId);

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
