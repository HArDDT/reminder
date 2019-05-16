package ua.tihonchik.dmitriy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Collection<Event>> getEvent(@PathVariable int userId) {
        Collection<Event> userEvents = eventService.getEvents(userId);
        return new ResponseEntity<>(userEvents, HttpStatus.OK);
    }

    @GetMapping(value = "/protected/get_event/{eventId}/{userId}")
    public Event getEvent(@PathVariable int eventId, @PathVariable int userId) {
        return eventService.getEvent(eventId, userId);
    }

    @PostMapping(value = "/protected/create_event")
    public ResponseEntity<Integer> createEvent(@RequestBody Event event) {
        int idEvent = eventService.createEvent(event);
        return new ResponseEntity<>(idEvent, HttpStatus.OK);
    }

    @PostMapping(value = "/protected/update_event")
    public void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }

    @DeleteMapping(value = "/protected/delete_event/{eventId}/{userId}")
    public void deleteEvent(@PathVariable int eventId, @PathVariable int userId) {
        eventService.deleteEvent(eventId, userId);
    }

}
