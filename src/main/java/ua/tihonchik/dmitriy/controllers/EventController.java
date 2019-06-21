package ua.tihonchik.dmitriy.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.services.EventService;

import java.util.Collection;

@RestController
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/protected/get_events/{userId}")
    public Collection<Event> getEvents(@PathVariable String userId) {
        return eventService.getEvents(userId);
    }

    @GetMapping(value = "/protected/get_event/{eventId}")
    public Event getEvent(@PathVariable String eventId) {
        return eventService.getEvent(eventId);
    }

    @PostMapping(value = "/protected/create_event")
    public Object createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PostMapping(value = "/protected/update_event")
    public void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }

    @DeleteMapping(value = "/protected/delete_event/{eventId}")
    public void deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(eventId);
    }

}
