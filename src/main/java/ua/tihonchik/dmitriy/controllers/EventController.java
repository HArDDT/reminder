package ua.tihonchik.dmitriy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.w3c.dom.events.Event;
import ua.tihonchik.dmitriy.services.EventService;

import java.util.List;

@Controller
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/protected/getevents/{userid}")
    public ResponseEntity<List<Event>> getEvent(@PathVariable int userId){
        return new ResponseEntity(List.of("2","1"), HttpStatus.OK);
    }
}
