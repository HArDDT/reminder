package ua.tihonchik.dmitriy.entities;

import ua.tihonchik.dmitriy.interfaces.Event;

import java.time.LocalDateTime;

public class EventImpl implements Event {

    private int id;
    private int userId;
    private int description;
    private LocalDateTime eventDate;
    private boolean activeEvent;
    private String reminderExpression;

}
