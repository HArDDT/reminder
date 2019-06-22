package ua.tihiy.reminder.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.tihiy.reminder.entities.Event;
import ua.tihiy.reminder.services.EventService;

import java.util.Collection;

@RestController
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/protected/events/{userId}")
    public Collection<Event> getEvents(@PathVariable int userId) {
        return eventService.getEvents(userId);
    }

    @GetMapping(value = "/protected/event/{eventId}")
    public Event getEvent(@PathVariable int eventId) {
        return eventService.getEvent(eventId);
    }

    @PostMapping(value = "/protected/event")
    public Object createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping(value = "/protected/event")
    public void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }

    @DeleteMapping(value = "/protected/event/{eventId}")
    public void deleteEvent(@PathVariable int eventId) {
        eventService.deleteEvent(eventId);
    }

}